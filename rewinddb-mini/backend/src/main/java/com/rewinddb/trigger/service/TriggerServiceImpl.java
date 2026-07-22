package com.rewinddb.trigger.service;

import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.common.enums.TriggerStatus;
import com.rewinddb.common.exception.ResourceNotFoundException;
import com.rewinddb.common.util.DateTimeUtils;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.security.CurrentUserProvider;
import com.rewinddb.trigger.dto.TriggerRequest;
import com.rewinddb.trigger.dto.TriggerResponse;
import com.rewinddb.trigger.entity.TriggerDefinition;
import com.rewinddb.trigger.mapper.TriggerDefinitionMapper;
import com.rewinddb.trigger.repository.TriggerDefinitionRepository;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TriggerServiceImpl implements TriggerService {

    private final TriggerDefinitionRepository triggerDefinitionRepository;
    private final TriggerDefinitionMapper triggerDefinitionMapper;
    private final DatabaseConnectionRepository connectionRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional
    public TriggerResponse create(TriggerRequest request) {
        AppUser owner = currentUserProvider.getCurrentUser();
        DatabaseConnection connection = connectionRepository
                .findByIdAndOwnerId(request.getConnectionId(), owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Database connection not found: " + request.getConnectionId()));

        Instant now = DateTimeUtils.now();
        TriggerDefinition trigger = triggerDefinitionMapper.toEntity(request);
        trigger.setConnection(connection);
        trigger.setTriggerName(generateTriggerName(request.getTableName()));
        trigger.setStatus(TriggerStatus.INSTALLED);
        trigger.setCreatedAt(now);
        trigger.setUpdatedAt(now);

        return triggerDefinitionMapper.toResponse(triggerDefinitionRepository.save(trigger));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TriggerResponse> findAll() {
        AppUser owner = currentUserProvider.getCurrentUser();
        return triggerDefinitionRepository.findByConnectionOwnerId(owner.getId()).stream()
                .map(triggerDefinitionMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TriggerResponse findById(UUID id) {
        return triggerDefinitionMapper.toResponse(getOwnedTrigger(id));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        triggerDefinitionRepository.delete(getOwnedTrigger(id));
    }

    private TriggerDefinition getOwnedTrigger(UUID id) {
        AppUser owner = currentUserProvider.getCurrentUser();
        return triggerDefinitionRepository.findByIdAndConnectionOwnerId(id, owner.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Trigger not found: " + id));
    }

    private String generateTriggerName(String tableName) {
        return "rewinddb_trg_" + tableName.toLowerCase() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
