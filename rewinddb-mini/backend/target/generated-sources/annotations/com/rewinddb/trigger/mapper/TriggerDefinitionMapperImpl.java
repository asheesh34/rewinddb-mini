package com.rewinddb.trigger.mapper;

import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.trigger.dto.TriggerRequest;
import com.rewinddb.trigger.dto.TriggerResponse;
import com.rewinddb.trigger.entity.TriggerDefinition;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class TriggerDefinitionMapperImpl implements TriggerDefinitionMapper {

    @Override
    public TriggerResponse toResponse(TriggerDefinition triggerDefinition) {
        if ( triggerDefinition == null ) {
            return null;
        }

        TriggerResponse.TriggerResponseBuilder triggerResponse = TriggerResponse.builder();

        triggerResponse.connectionId( triggerDefinitionConnectionId( triggerDefinition ) );
        triggerResponse.id( triggerDefinition.getId() );
        triggerResponse.schemaName( triggerDefinition.getSchemaName() );
        triggerResponse.tableName( triggerDefinition.getTableName() );
        triggerResponse.triggerName( triggerDefinition.getTriggerName() );
        triggerResponse.status( triggerDefinition.getStatus() );
        triggerResponse.createdAt( triggerDefinition.getCreatedAt() );
        triggerResponse.updatedAt( triggerDefinition.getUpdatedAt() );

        return triggerResponse.build();
    }

    @Override
    public TriggerDefinition toEntity(TriggerRequest request) {
        if ( request == null ) {
            return null;
        }

        TriggerDefinition.TriggerDefinitionBuilder triggerDefinition = TriggerDefinition.builder();

        triggerDefinition.schemaName( request.getSchemaName() );
        triggerDefinition.tableName( request.getTableName() );

        return triggerDefinition.build();
    }

    private UUID triggerDefinitionConnectionId(TriggerDefinition triggerDefinition) {
        DatabaseConnection connection = triggerDefinition.getConnection();
        if ( connection == null ) {
            return null;
        }
        return connection.getId();
    }
}
