package com.rewinddb.rollback.dto;

import com.rewinddb.common.enums.RollbackStatus;
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
public class RollbackResponse {
    private UUID id;
    private UUID connectionId;
    private UUID requestedBy;
    private Long targetVersion;
    private RollbackStatus status;
    private String failureReason;
    private Instant requestedAt;
    private Instant completedAt;
}
