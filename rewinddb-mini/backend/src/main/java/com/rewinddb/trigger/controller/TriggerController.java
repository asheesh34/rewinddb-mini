package com.rewinddb.trigger.controller;

import com.rewinddb.trigger.dto.TriggerRequest;
import com.rewinddb.trigger.dto.TriggerResponse;
import com.rewinddb.trigger.service.TriggerService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/triggers")
@RequiredArgsConstructor
@Tag(name = "Triggers", description = "Manage change-capture trigger definitions for a connection's tables")
public class TriggerController {

    private final TriggerService triggerService;

    @PostMapping
    public ResponseEntity<TriggerResponse> create(@Valid @RequestBody TriggerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(triggerService.create(request));
    }

    @GetMapping
    public ResponseEntity<List<TriggerResponse>> findAll() {
        return ResponseEntity.ok(triggerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TriggerResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(triggerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        triggerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
