package com.rewinddb.history.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionCompareRequest {
    @NotNull
    private UUID connectionId;

    @NotNull
    @Positive
    private Long sourceVersion;

    @NotNull
    @Positive
    private Long targetVersion;
}
