package com.rewinddb.rollback.controller;

import com.rewinddb.rollback.dto.RollbackRequest;
import com.rewinddb.rollback.dto.RollbackResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
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
public class RollbackController {
    @PostMapping
    public ResponseEntity<RollbackResponse> requestRollback(@Valid @RequestBody RollbackRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping
    public ResponseEntity<List<RollbackResponse>> findByConnection(@RequestParam UUID connectionId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
