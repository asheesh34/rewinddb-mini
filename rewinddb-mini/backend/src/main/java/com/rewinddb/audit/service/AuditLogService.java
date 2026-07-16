package com.rewinddb.audit.service;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.dto.AuditLogResponse;
import java.util.List;
import java.util.UUID;

public interface AuditLogService {
    AuditLogResponse create(AuditLogRequest request);

    List<AuditLogResponse> findByUser(UUID userId);
}
