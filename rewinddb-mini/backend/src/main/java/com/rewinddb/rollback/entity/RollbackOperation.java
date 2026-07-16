package com.rewinddb.rollback.entity;

import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.enums.RollbackStatus;
import com.rewinddb.connection.entity.DatabaseConnection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "rollback_operations")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RollbackOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_id", nullable = false)
    private DatabaseConnection connection;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by", nullable = false)
    private AppUser requestedBy;

    @NotNull
    @Column(nullable = false)
    private Long targetVersion;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RollbackStatus status;

    @Column(columnDefinition = "text")
    private String failureReason;

    @Column(nullable = false, updatable = false)
    private Instant requestedAt;

    private Instant completedAt;
}
