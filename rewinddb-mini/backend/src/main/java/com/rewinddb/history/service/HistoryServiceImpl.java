package com.rewinddb.history.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.exception.ResourceNotFoundException;
import com.rewinddb.common.util.JsonUtils;
import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.dto.VersionCompareRequest;
import com.rewinddb.history.dto.VersionCompareResponse;
import com.rewinddb.history.entity.HistoryVersion;
import com.rewinddb.history.mapper.HistoryVersionMapper;
import com.rewinddb.history.repository.HistoryVersionRepository;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.security.CurrentUserProvider;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    private final HistoryVersionRepository historyVersionRepository;
    private final HistoryVersionMapper historyVersionMapper;
    private final DatabaseConnectionRepository connectionRepository;
    private final CurrentUserProvider currentUserProvider;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional(readOnly = true)
    public List<HistoryVersionResponse> findByConnection(UUID connectionId) {
        assertOwnership(connectionId);
        return historyVersionRepository.findByConnectionIdOrderByVersionNumberDesc(connectionId).stream()
                .map(historyVersionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public HistoryVersionResponse findVersion(UUID connectionId, Long versionNumber) {
        assertOwnership(connectionId);
        return historyVersionMapper.toResponse(getVersion(connectionId, versionNumber));
    }

    @Override
    @Transactional(readOnly = true)
    public VersionCompareResponse compare(VersionCompareRequest request) {
        assertOwnership(request.getConnectionId());
        HistoryVersion source = getVersion(request.getConnectionId(), request.getSourceVersion());
        HistoryVersion target = getVersion(request.getConnectionId(), request.getTargetVersion());

        String diff = JsonUtils.diff(source.getRowSnapshot(), target.getRowSnapshot(), objectMapper);

        return VersionCompareResponse.builder()
                .connectionId(request.getConnectionId())
                .sourceVersion(request.getSourceVersion())
                .targetVersion(request.getTargetVersion())
                .diff(diff)
                .build();
    }

    private HistoryVersion getVersion(UUID connectionId, Long versionNumber) {
        return historyVersionRepository.findByConnectionIdAndVersionNumber(connectionId, versionNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Version " + versionNumber + " not found for connection " + connectionId));
    }

    private void assertOwnership(UUID connectionId) {
        AppUser owner = currentUserProvider.getCurrentUser();
        connectionRepository.findByIdAndOwnerId(connectionId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Database connection not found: " + connectionId));
    }
}
