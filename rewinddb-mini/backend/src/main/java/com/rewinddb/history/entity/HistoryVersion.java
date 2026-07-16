package com.rewinddb.history.entity;

import com.rewinddb.capture.entity.ChangeEvent;
import com.rewinddb.connection.entity.DatabaseConnection;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "history_versions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_id", nullable = false)
    private DatabaseConnection connection;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "change_event_id", nullable = false)
    private ChangeEvent changeEvent;

    @Positive
    @Column(nullable = false)
    private Long versionNumber;

    @NotBlank
    @Column(nullable = false)
    private String schemaName;

    @NotBlank
    @Column(nullable = false)
    private String tableName;

    @Column(columnDefinition = "jsonb")
    private String rowSnapshot;

    @Column(nullable = false, updatable = false)
    private Instant createdAt;
}
