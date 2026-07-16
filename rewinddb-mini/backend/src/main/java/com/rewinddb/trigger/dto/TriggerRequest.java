package com.rewinddb.trigger.dto;

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
public class TriggerRequest {
    @NotNull
    private UUID connectionId;

    @NotBlank
    private String schemaName;

    @NotBlank
    private String tableName;
}
