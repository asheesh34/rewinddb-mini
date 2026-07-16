package com.rewinddb.capture.entity;

import com.rewinddb.common.enums.ChangeStatus;
import com.rewinddb.common.enums.OperationType;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.trigger.entity.TriggerDefinition;
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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "change_events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "connection_id", nullable = false)
    private DatabaseConnection connection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trigger_id")
    private TriggerDefinition trigger;

    @NotBlank
    @Column(nullable = false)
    private String schemaName;

    @NotBlank
    @Column(nullable = false)
    private String tableName;

    @NotBlank
    @Column(nullable = false)
    private String primaryKeyValue;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OperationType operationType;

    @Column(columnDefinition = "jsonb")
    private String beforeData;

    @Column(columnDefinition = "jsonb")
    private String afterData;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChangeStatus status;

    @Column(nullable = false, updatable = false)
    private Instant capturedAt;
}
