package com.rewinddb.history.repository;

import com.rewinddb.history.entity.HistoryVersion;
import java.util.Optional;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryVersionRepository extends JpaRepository<HistoryVersion, UUID> {
    List<HistoryVersion> findByConnectionIdOrderByVersionNumberDesc(UUID connectionId);

    Optional<HistoryVersion> findByConnectionIdAndVersionNumber(UUID connectionId, Long versionNumber);

    Optional<HistoryVersion> findTopByConnectionIdOrderByVersionNumberDesc(UUID connectionId);
}
