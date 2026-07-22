package com.rewinddb.audit.service;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.dto.AuditLogResponse;
import com.rewinddb.audit.entity.AuditLog;
import com.rewinddb.audit.mapper.AuditLogMapper;
import com.rewinddb.audit.repository.AuditLogRepository;
import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.enums.UserRole;
import com.rewinddb.common.exception.ForbiddenException;
import com.rewinddb.common.util.DateTimeUtils;
import com.rewinddb.security.CurrentUserProvider;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional
    public AuditLogResponse create(AuditLogRequest request) {
        AppUser actingUser = currentUserProvider.getCurrentUser();

        AuditLog auditLog = auditLogMapper.toEntity(request);
        auditLog.setUser(actingUser);
        auditLog.setCreatedAt(DateTimeUtils.now());

        return auditLogMapper.toResponse(auditLogRepository.save(auditLog));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> findByUser(UUID userId) {
        AppUser requester = currentUserProvider.getCurrentUser();
        boolean isSelf = requester.getId().equals(userId);
        boolean isAdmin = requester.getRole() == UserRole.ADMIN;
        if (!isSelf && !isAdmin) {
            throw new ForbiddenException("You may only view your own audit history");
        }

        return auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }
}
