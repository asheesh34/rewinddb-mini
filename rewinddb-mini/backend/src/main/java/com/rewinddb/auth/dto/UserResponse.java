package com.rewinddb.auth.dto;

import com.rewinddb.common.enums.UserRole;
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
public class UserResponse {
    private UUID id;
    private String email;
    private String displayName;
    private UserRole role;
    private boolean enabled;
    private Instant createdAt;
    private Instant updatedAt;
}
