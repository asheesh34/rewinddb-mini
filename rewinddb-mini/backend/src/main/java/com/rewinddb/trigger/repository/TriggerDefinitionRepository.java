package com.rewinddb.trigger.repository;

import com.rewinddb.trigger.entity.TriggerDefinition;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TriggerDefinitionRepository extends JpaRepository<TriggerDefinition, UUID> {
}
