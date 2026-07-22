package com.rewinddb.capture.service;

import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.capture.dto.ChangeEventRequest;
import com.rewinddb.capture.dto.ChangeEventResponse;
import com.rewinddb.capture.entity.ChangeEvent;
import com.rewinddb.capture.mapper.ChangeEventMapper;
import com.rewinddb.capture.repository.ChangeEventRepository;
import com.rewinddb.common.enums.ChangeStatus;
import com.rewinddb.common.exception.ResourceNotFoundException;
import com.rewinddb.common.util.DateTimeUtils;
import com.rewinddb.common.util.StringUtils;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.history.entity.HistoryVersion;
import com.rewinddb.history.repository.HistoryVersionRepository;
import com.rewinddb.security.CurrentUserProvider;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Accepts change events reported for a connection (in a full deployment, a lightweight
 * agent installed next to the target database would call this endpoint whenever a row
 * changes) and turns each one into the next {@link HistoryVersion} snapshot for that row.
 */
@Service
@RequiredArgsConstructor
public class ChangeCaptureServiceImpl implements ChangeCaptureService {

    private final ChangeEventRepository changeEventRepository;
    private final ChangeEventMapper changeEventMapper;
    private final DatabaseConnectionRepository connectionRepository;
    private final HistoryVersionRepository historyVersionRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional
    public ChangeEventResponse record(ChangeEventRequest request) {
        DatabaseConnection connection = getOwnedConnection(request.getConnectionId());

        Instant now = DateTimeUtils.now();
        ChangeEvent event = changeEventMapper.toEntity(request);
        event.setConnection(connection);
        event.setStatus(ChangeStatus.CAPTURED);
        event.setCapturedAt(now);
        ChangeEvent savedEvent = changeEventRepository.save(event);

        long nextVersionNumber = historyVersionRepository
                .findTopByConnectionIdOrderByVersionNumberDesc(connection.getId())
                .map(previous -> previous.getVersionNumber() + 1)
                .orElse(1L);

        String snapshot = StringUtils.hasText(request.getAfterData())
                ? request.getAfterData()
                : request.getBeforeData();

        HistoryVersion version = HistoryVersion.builder()
                .connection(connection)
                .changeEvent(savedEvent)
                .versionNumber(nextVersionNumber)
                .schemaName(request.getSchemaName())
                .tableName(request.getTableName())
                .rowSnapshot(snapshot)
                .createdAt(now)
                .build();
        historyVersionRepository.save(version);

        savedEvent.setStatus(ChangeStatus.PROCESSED);
        return changeEventMapper.toResponse(changeEventRepository.save(savedEvent));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChangeEventResponse> findByConnection(UUID connectionId) {
        getOwnedConnection(connectionId);
        return changeEventRepository.findByConnectionIdOrderByCapturedAtDesc(connectionId).stream()
                .map(changeEventMapper::toResponse)
                .toList();
    }

    private DatabaseConnection getOwnedConnection(UUID connectionId) {
        AppUser owner = currentUserProvider.getCurrentUser();
        return connectionRepository.findByIdAndOwnerId(connectionId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Database connection not found: " + connectionId));
    }
}
