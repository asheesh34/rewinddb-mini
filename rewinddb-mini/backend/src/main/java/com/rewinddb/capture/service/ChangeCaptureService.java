package com.rewinddb.capture.service;

import com.rewinddb.capture.dto.ChangeEventRequest;
import com.rewinddb.capture.dto.ChangeEventResponse;
import java.util.List;
import java.util.UUID;

public interface ChangeCaptureService {
    ChangeEventResponse record(ChangeEventRequest request);

    List<ChangeEventResponse> findByConnection(UUID connectionId);
}
