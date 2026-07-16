package com.rewinddb.audit.dto;

import com.rewinddb.common.enums.AuditAction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogRequest {
    private UUID userId;

    @NotNull
    private AuditAction action;

    @NotBlank
    private String resourceType;

    private String resourceId;
    private String metadata;
    private String ipAddress;
}
