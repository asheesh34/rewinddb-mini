package com.rewinddb.connection.service;

import com.rewinddb.connection.dto.DatabaseConnectionRequest;
import com.rewinddb.connection.dto.DatabaseConnectionResponse;
import java.util.List;
import java.util.UUID;

public interface DatabaseConnectionService {
    DatabaseConnectionResponse create(DatabaseConnectionRequest request);

    List<DatabaseConnectionResponse> findAll();

    DatabaseConnectionResponse findById(UUID id);

    DatabaseConnectionResponse update(UUID id, DatabaseConnectionRequest request);

    void delete(UUID id);
}
