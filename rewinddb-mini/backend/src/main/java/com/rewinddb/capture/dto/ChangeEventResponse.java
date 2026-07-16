package com.rewinddb.capture.dto;

import com.rewinddb.common.enums.ChangeStatus;
import com.rewinddb.common.enums.OperationType;
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
public class ChangeEventResponse {
    private UUID id;
    private UUID connectionId;
    private UUID triggerId;
    private String schemaName;
    private String tableName;
    private String primaryKeyValue;
    private OperationType operationType;
    private String beforeData;
    private String afterData;
    private ChangeStatus status;
    private Instant capturedAt;
}
