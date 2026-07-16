package com.rewinddb.connection.dto;

import com.rewinddb.common.enums.ConnectionStatus;
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
public class DatabaseConnectionResponse {
    private UUID id;
    private String name;
    private String host;
    private Integer port;
    private String databaseName;
    private String username;
    private ConnectionStatus status;
    private Instant createdAt;
    private Instant updatedAt;
}
