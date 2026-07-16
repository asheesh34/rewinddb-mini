package com.rewinddb.history.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VersionCompareResponse {
    private UUID connectionId;
    private Long sourceVersion;
    private Long targetVersion;
    private String diff;
}
