package com.rewinddb.capture.dto;

import com.rewinddb.common.enums.OperationType;
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
public class ChangeEventRequest {
    @NotNull
    private UUID connectionId;

    @NotBlank
    private String schemaName;

    @NotBlank
    private String tableName;

    @NotBlank
    private String primaryKeyValue;

    @NotNull
    private OperationType operationType;

    private String beforeData;
    private String afterData;
}
