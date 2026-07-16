package com.rewinddb.connection.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConnectionRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String host;

    @Positive
    private Integer port;

    @NotBlank
    private String databaseName;

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
