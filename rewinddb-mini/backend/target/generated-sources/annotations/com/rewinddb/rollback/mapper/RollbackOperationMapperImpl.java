package com.rewinddb.rollback.mapper;

import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.rollback.dto.RollbackResponse;
import com.rewinddb.rollback.entity.RollbackOperation;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class RollbackOperationMapperImpl implements RollbackOperationMapper {

    @Override
    public RollbackResponse toResponse(RollbackOperation rollbackOperation) {
        if ( rollbackOperation == null ) {
            return null;
        }

        RollbackResponse.RollbackResponseBuilder rollbackResponse = RollbackResponse.builder();

        rollbackResponse.connectionId( rollbackOperationConnectionId( rollbackOperation ) );
        rollbackResponse.requestedBy( rollbackOperationRequestedById( rollbackOperation ) );
        rollbackResponse.id( rollbackOperation.getId() );
        rollbackResponse.targetVersion( rollbackOperation.getTargetVersion() );
        rollbackResponse.status( rollbackOperation.getStatus() );
        rollbackResponse.failureReason( rollbackOperation.getFailureReason() );
        rollbackResponse.requestedAt( rollbackOperation.getRequestedAt() );
        rollbackResponse.completedAt( rollbackOperation.getCompletedAt() );

        return rollbackResponse.build();
    }

    private UUID rollbackOperationConnectionId(RollbackOperation rollbackOperation) {
        DatabaseConnection connection = rollbackOperation.getConnection();
        if ( connection == null ) {
            return null;
        }
        return connection.getId();
    }

    private UUID rollbackOperationRequestedById(RollbackOperation rollbackOperation) {
        AppUser requestedBy = rollbackOperation.getRequestedBy();
        if ( requestedBy == null ) {
            return null;
        }
        return requestedBy.getId();
    }
}
