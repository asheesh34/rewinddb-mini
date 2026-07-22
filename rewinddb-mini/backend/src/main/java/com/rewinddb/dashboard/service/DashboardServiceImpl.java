package com.rewinddb.dashboard.service;

import com.rewinddb.capture.repository.ChangeEventRepository;
import com.rewinddb.connection.repository.DatabaseConnectionRepository;
import com.rewinddb.dashboard.dto.DashboardSummaryResponse;
import com.rewinddb.rollback.repository.RollbackOperationRepository;
import com.rewinddb.security.CurrentUserProvider;
import com.rewinddb.trigger.repository.TriggerDefinitionRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final DatabaseConnectionRepository connectionRepository;
    private final TriggerDefinitionRepository triggerDefinitionRepository;
    private final ChangeEventRepository changeEventRepository;
    private final RollbackOperationRepository rollbackOperationRepository;
    private final CurrentUserProvider currentUserProvider;

    @Override
    @Transactional(readOnly = true)
    public DashboardSummaryResponse summary() {
        UUID ownerId = currentUserProvider.getCurrentUser().getId();
        return DashboardSummaryResponse.builder()
                .connectionCount(connectionRepository.countByOwnerId(ownerId))
                .triggerCount(triggerDefinitionRepository.countByConnectionOwnerId(ownerId))
                .capturedChangeCount(changeEventRepository.countByConnectionOwnerId(ownerId))
                .rollbackCount(rollbackOperationRepository.countByConnectionOwnerId(ownerId))
                .build();
    }
}
