package com.rewinddb.audit.controller;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.dto.AuditLogResponse;
import com.rewinddb.audit.service.AuditLogService;
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
@RequestMapping("/api/v1/audit-logs")
@RequiredArgsConstructor
@Tag(name = "Audit Logs", description = "Security-relevant action history")
public class AuditLogController {

    private final AuditLogService auditLogService;

    @PostMapping
    public ResponseEntity<AuditLogResponse> create(@Valid @RequestBody AuditLogRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(auditLogService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> findByUser(@RequestParam UUID userId) {
        return ResponseEntity.ok(auditLogService.findByUser(userId));
    }
}
