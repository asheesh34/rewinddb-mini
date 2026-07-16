package com.rewinddb.audit.dto;

import com.rewinddb.common.enums.AuditAction;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogResponse {
    private UUID id;
    private UUID userId;
    private AuditAction action;
    private String resourceType;
    private String resourceId;
    private String metadata;
    private String ipAddress;
    private Instant createdAt;
}
