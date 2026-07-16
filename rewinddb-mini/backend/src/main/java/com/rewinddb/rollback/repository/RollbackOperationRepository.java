package com.rewinddb.rollback.repository;

import com.rewinddb.rollback.entity.RollbackOperation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RollbackOperationRepository extends JpaRepository<RollbackOperation, UUID> {
}
