package com.rewinddb.trigger.repository;

import com.rewinddb.trigger.entity.TriggerDefinition;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriggerDefinitionRepository extends JpaRepository<TriggerDefinition, UUID> {
    List<TriggerDefinition> findByConnectionOwnerId(UUID ownerId);

    Optional<TriggerDefinition> findByIdAndConnectionOwnerId(UUID id, UUID ownerId);

    long countByConnectionOwnerId(UUID ownerId);
}
