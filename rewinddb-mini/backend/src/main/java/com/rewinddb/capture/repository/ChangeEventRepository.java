package com.rewinddb.capture.repository;

import com.rewinddb.capture.entity.ChangeEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeEventRepository extends JpaRepository<ChangeEvent, UUID> {
    List<ChangeEvent> findByConnectionIdOrderByCapturedAtDesc(UUID connectionId);

    long countByConnectionOwnerId(UUID ownerId);
}
