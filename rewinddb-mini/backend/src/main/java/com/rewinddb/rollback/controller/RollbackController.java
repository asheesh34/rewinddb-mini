package com.rewinddb.rollback.controller;

import com.rewinddb.rollback.dto.RollbackRequest;
import com.rewinddb.rollback.dto.RollbackResponse;
import com.rewinddb.rollback.service.RollbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rollback")
@RequiredArgsConstructor
@Tag(name = "Rollback", description = "Restore a connection's data to a previous version")
public class RollbackController {

    private final RollbackService rollbackService;

    @PostMapping
    public ResponseEntity<RollbackResponse> requestRollback(@Valid @RequestBody RollbackRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(rollbackService.requestRollback(request));
    }

    @GetMapping
    public ResponseEntity<List<RollbackResponse>> findByConnection(@RequestParam UUID connectionId) {
        return ResponseEntity.ok(rollbackService.findByConnection(connectionId));
    }
}
