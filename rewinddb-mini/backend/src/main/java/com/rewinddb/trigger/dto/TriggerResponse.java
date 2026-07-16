package com.rewinddb.trigger.dto;

import com.rewinddb.common.enums.TriggerStatus;
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
public class TriggerResponse {
    private UUID id;
    private UUID connectionId;
    private String schemaName;
    private String tableName;
    private String triggerName;
    private TriggerStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
