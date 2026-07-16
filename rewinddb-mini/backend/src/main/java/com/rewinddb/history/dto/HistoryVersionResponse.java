package com.rewinddb.history.dto;

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
public class HistoryVersionResponse {
    private UUID id;
    private UUID connectionId;
    private UUID changeEventId;
    private Long versionNumber;
    private String schemaName;
    private String tableName;
    private String rowSnapshot;
    private Instant createdAt;
}
