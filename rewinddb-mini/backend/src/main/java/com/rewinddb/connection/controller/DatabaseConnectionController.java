package com.rewinddb.connection.controller;

import com.rewinddb.connection.dto.DatabaseConnectionRequest;
import com.rewinddb.connection.dto.DatabaseConnectionResponse;
import com.rewinddb.connection.service.DatabaseConnectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/connections")
@RequiredArgsConstructor
@Tag(name = "Database Connections", description = "Register and manage target database connections")
public class DatabaseConnectionController {

    private final DatabaseConnectionService connectionService;

    @PostMapping
    public ResponseEntity<DatabaseConnectionResponse> create(@Valid @RequestBody DatabaseConnectionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(connectionService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<DatabaseConnectionResponse>> findAll() {
        return ResponseEntity.ok(connectionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatabaseConnectionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(connectionService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatabaseConnectionResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody DatabaseConnectionRequest request) {
        return ResponseEntity.ok(connectionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        connectionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
