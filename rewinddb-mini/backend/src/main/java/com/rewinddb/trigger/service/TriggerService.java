package com.rewinddb.trigger.service;

import com.rewinddb.trigger.dto.TriggerRequest;
import com.rewinddb.trigger.dto.TriggerResponse;
import java.util.List;
import java.util.UUID;

public interface TriggerService {
    TriggerResponse create(TriggerRequest request);

    List<TriggerResponse> findAll();

    TriggerResponse findById(UUID id);

    void delete(UUID id);
}
