package com.rewinddb.history.repository;

import com.rewinddb.history.entity.HistoryVersion;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryVersionRepository extends JpaRepository<HistoryVersion, UUID> {
}
