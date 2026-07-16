package com.rewinddb.rollback.service;

import com.rewinddb.rollback.dto.RollbackRequest;
import com.rewinddb.rollback.dto.RollbackResponse;
import java.util.List;
import java.util.UUID;

public interface RollbackService {
    RollbackResponse requestRollback(RollbackRequest request);

    List<RollbackResponse> findByConnection(UUID connectionId);
}
