package com.rewinddb.connection.repository;

import com.rewinddb.connection.entity.DatabaseConnection;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatabaseConnectionRepository extends JpaRepository<DatabaseConnection, UUID> {
}
