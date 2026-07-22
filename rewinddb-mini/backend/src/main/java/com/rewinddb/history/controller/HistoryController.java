package com.rewinddb.history.controller;

import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.dto.VersionCompareRequest;
import com.rewinddb.history.dto.VersionCompareResponse;
import com.rewinddb.history.service.HistoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@Tag(name = "Version History", description = "Browse and compare row-level version history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<HistoryVersionResponse>> findByConnection(@RequestParam UUID connectionId) {
        return ResponseEntity.ok(historyService.findByConnection(connectionId));
    }

    @GetMapping("/{versionNumber}")
    public ResponseEntity<HistoryVersionResponse> findVersion(
            @RequestParam UUID connectionId,
            @PathVariable Long versionNumber) {
        return ResponseEntity.ok(historyService.findVersion(connectionId, versionNumber));
    }

    @PostMapping("/compare")
    public ResponseEntity<VersionCompareResponse> compare(@Valid @RequestBody VersionCompareRequest request) {
        return ResponseEntity.ok(historyService.compare(request));
    }
}
