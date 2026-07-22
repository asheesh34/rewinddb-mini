package com.rewinddb.capture.controller;

import com.rewinddb.capture.dto.ChangeEventRequest;
import com.rewinddb.capture.dto.ChangeEventResponse;
import com.rewinddb.capture.service.ChangeCaptureService;
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
@RequestMapping("/api/v1/change-events")
@RequiredArgsConstructor
@Tag(name = "Change Capture", description = "Report row-level changes captured for a connection")
public class ChangeCaptureController {

    private final ChangeCaptureService changeCaptureService;

    @PostMapping
    public ResponseEntity<ChangeEventResponse> record(@Valid @RequestBody ChangeEventRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(changeCaptureService.record(request));
    }

    @GetMapping
    public ResponseEntity<List<ChangeEventResponse>> findByConnection(@RequestParam UUID connectionId) {
        return ResponseEntity.ok(changeCaptureService.findByConnection(connectionId));
    }
}
