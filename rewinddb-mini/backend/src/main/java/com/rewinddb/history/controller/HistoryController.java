package com.rewinddb.history.controller;

import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.dto.VersionCompareRequest;
import com.rewinddb.history.dto.VersionCompareResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/history")
public class HistoryController {
    @GetMapping
    public ResponseEntity<List<HistoryVersionResponse>> findByConnection(@RequestParam UUID connectionId) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{versionNumber}")
    public ResponseEntity<HistoryVersionResponse> findVersion(
            @RequestParam UUID connectionId,
            @PathVariable Long versionNumber) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/compare")
    public ResponseEntity<VersionCompareResponse> compare(@Valid @RequestBody VersionCompareRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
