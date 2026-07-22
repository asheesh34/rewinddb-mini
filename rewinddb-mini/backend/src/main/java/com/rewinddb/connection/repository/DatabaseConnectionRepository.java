package com.rewinddb.connection.repository;

import com.rewinddb.connection.entity.DatabaseConnection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, UUID> {
    List<DatabaseConnection> findByOwnerIdOrderByCreatedAtDesc(UUID ownerId);

    Optional<DatabaseConnection> findByIdAndOwnerId(UUID id, UUID ownerId);

    long countByOwnerId(UUID ownerId);
}
