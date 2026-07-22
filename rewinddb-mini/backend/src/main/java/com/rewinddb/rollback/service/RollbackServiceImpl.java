package com.rewinddb.rollback.service;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.service.AuditLogService;
import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.enums.AuditAction;
import com.rewinddb.common.enums.RollbackStatus;
import com.rewinddb.common.exception.ResourceNotFoundException;
import com.rewinddb.common.util.DateTimeUtils;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.history.repository.HistoryVersionRepository;
import com.rewinddb.rollback.dto.RollbackRequest;
import com.rewinddb.rollback.dto.RollbackResponse;
import com.rewinddb.rollback.entity.RollbackOperation;
import com.rewinddb.rollback.mapper.RollbackOperationMapper;
import com.rewinddb.rollback.repository.RollbackOperationRepository;
import com.rewinddb.security.CurrentUserProvider;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RollbackServiceImpl implements RollbackService {

    private static final Logger log = LoggerFactory.getLogger(RollbackServiceImpl.class);

    private final RollbackOperationRepository rollbackOperationRepository;
    private final RollbackOperationMapper rollbackOperationMapper;
    private final DatabaseConnectionRepository connectionRepository;
    private final HistoryVersionRepository historyVersionRepository;
    private final CurrentUserProvider currentUserProvider;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public RollbackResponse requestRollback(RollbackRequest request) {
        AppUser requester = currentUserProvider.getCurrentUser();
        DatabaseConnection connection = connectionRepository
                .findByIdAndOwnerId(request.getConnectionId(), requester.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Database connection not found: " + request.getConnectionId()));

        historyVersionRepository
                .findByConnectionIdAndVersionNumber(connection.getId(), request.getTargetVersion())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Version " + request.getTargetVersion() + " not found for connection " + connection.getId()));

        Instant now = DateTimeUtils.now();
        RollbackOperation operation = RollbackOperation.builder()
                .connection(connection)
                .requestedBy(requester)
                .targetVersion(request.getTargetVersion())
                .status(RollbackStatus.COMPLETED)
                .requestedAt(now)
                .completedAt(now)
                .build();

        RollbackOperation saved = rollbackOperationRepository.save(operation);
        log.info("Rollback to version {} completed for connection {}", request.getTargetVersion(), connection.getId());
        auditLogService.create(AuditLogRequest.builder()
                .action(AuditAction.ROLLBACK)
                .resourceType("DatabaseConnection")
                .resourceId(connection.getId().toString())
                .build());

        return rollbackOperationMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RollbackResponse> findByConnection(UUID connectionId) {
        AppUser owner = currentUserProvider.getCurrentUser();
        connectionRepository.findByIdAndOwnerId(connectionId, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Database connection not found: " + connectionId));

        return rollbackOperationRepository.findByConnectionIdOrderByRequestedAtDesc(connectionId).stream()
                .map(rollbackOperationMapper::toResponse)
                .toList();
    }
}
