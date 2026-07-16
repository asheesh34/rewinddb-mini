package com.rewinddb.history.service;

import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.dto.VersionCompareRequest;
import com.rewinddb.history.dto.VersionCompareResponse;
import java.util.List;
import java.util.UUID;

public interface HistoryService {
    List<HistoryVersionResponse> findByConnection(UUID connectionId);

    HistoryVersionResponse findVersion(UUID connectionId, Long versionNumber);

    VersionCompareResponse compare(VersionCompareRequest request);
}
