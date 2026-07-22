package com.rewinddb.connection.service;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.service.AuditLogService;
import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.enums.AuditAction;
import com.rewinddb.common.enums.ConnectionStatus;
import com.rewinddb.common.exception.ResourceNotFoundException;
import com.rewinddb.common.util.DateTimeUtils;
import com.rewinddb.connection.dto.DatabaseConnectionRequest;
import com.rewinddb.connection.dto.DatabaseConnectionResponse;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.connection.mapper.DatabaseConnectionMapper;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.security.CurrentUserProvider;
import com.rewinddb.security.EncryptionService;
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
public class DatabaseConnectionServiceImpl implements DatabaseConnectionService {

    private static final Logger log = LoggerFactory.getLogger(DatabaseConnectionServiceImpl.class);

    private final DatabaseConnectionRepository connectionRepository;
    private final DatabaseConnectionMapper connectionMapper;
    private final EncryptionService encryptionService;
    private final CurrentUserProvider currentUserProvider;
    private final AuditLogService auditLogService;

    @Override
    @Transactional
    public DatabaseConnectionResponse create(DatabaseConnectionRequest request) {
        AppUser owner = currentUserProvider.getCurrentUser();

        Instant now = DateTimeUtils.now();
        DatabaseConnection connection = connectionMapper.toEntity(request);
        connection.setOwner(owner);
        connection.setEncryptedPassword(encryptionService.encrypt(request.getPassword()));
        connection.setStatus(ConnectionStatus.ACTIVE);
        connection.setCreatedAt(now);
        connection.setUpdatedAt(now);

        DatabaseConnection saved = connectionRepository.save(connection);
        log.info("Database connection '{}' created by user {}", saved.getName(), owner.getId());
        auditLogService.create(AuditLogRequest.builder()
                .action(AuditAction.CREATE)
                .resourceType("DatabaseConnection")
                .resourceId(saved.getId().toString())
                .build());

        return connectionMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DatabaseConnectionResponse> findAll() {
        AppUser owner = currentUserProvider.getCurrentUser();
        return connectionRepository.findByOwnerIdOrderByCreatedAtDesc(owner.getId()).stream()
                .map(connectionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DatabaseConnectionResponse findById(UUID id) {
        return connectionMapper.toResponse(getOwnedConnection(id));
    }

    @Override
    @Transactional
    public DatabaseConnectionResponse update(UUID id, DatabaseConnectionRequest request) {
        DatabaseConnection connection = getOwnedConnection(id);
        connection.setName(request.getName());
        connection.setHost(request.getHost());
        connection.setPort(request.getPort());
        connection.setDatabaseName(request.getDatabaseName());
        connection.setUsername(request.getUsername());
        connection.setEncryptedPassword(encryptionService.encrypt(request.getPassword()));
        connection.setUpdatedAt(DateTimeUtils.now());

        DatabaseConnection saved = connectionRepository.save(connection);
        auditLogService.create(AuditLogRequest.builder()
                .action(AuditAction.UPDATE)
                .resourceType("DatabaseConnection")
                .resourceId(saved.getId().toString())
                .build());

        return connectionMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        DatabaseConnection connection = getOwnedConnection(id);
        connectionRepository.delete(connection);
        auditLogService.create(AuditLogRequest.builder()
                .action(AuditAction.DELETE)
                .resourceType("DatabaseConnection")
                .resourceId(id.toString())
                .build());
    }

    /**
     * Loads a connection scoped to the current owner. Returning 404 for both
     * "doesn't exist" and "belongs to someone else" avoids leaking the existence
     * of other users' resources.
     */
    private DatabaseConnection getOwnedConnection(UUID id) {
        AppUser owner = currentUserProvider.getCurrentUser();
        return connectionRepository.findByIdAndOwnerId(id, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Database connection not found: " + id));
    }
}
