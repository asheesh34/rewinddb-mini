package com.rewinddb.capture.mapper;

import com.rewinddb.capture.dto.ChangeEventRequest;
import com.rewinddb.capture.dto.ChangeEventResponse;
import com.rewinddb.capture.entity.ChangeEvent;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.trigger.entity.TriggerDefinition;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:53+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class ChangeEventMapperImpl implements ChangeEventMapper {

    @Override
    public ChangeEventResponse toResponse(ChangeEvent changeEvent) {
        if ( changeEvent == null ) {
            return null;
        }

        ChangeEventResponse.ChangeEventResponseBuilder changeEventResponse = ChangeEventResponse.builder();

        changeEventResponse.connectionId( changeEventConnectionId( changeEvent ) );
        changeEventResponse.triggerId( changeEventTriggerId( changeEvent ) );
        changeEventResponse.id( changeEvent.getId() );
        changeEventResponse.schemaName( changeEvent.getSchemaName() );
        changeEventResponse.tableName( changeEvent.getTableName() );
        changeEventResponse.primaryKeyValue( changeEvent.getPrimaryKeyValue() );
        changeEventResponse.operationType( changeEvent.getOperationType() );
        changeEventResponse.beforeData( changeEvent.getBeforeData() );
        changeEventResponse.afterData( changeEvent.getAfterData() );
        changeEventResponse.status( changeEvent.getStatus() );
        changeEventResponse.capturedAt( changeEvent.getCapturedAt() );

        return changeEventResponse.build();
    }

    @Override
    public ChangeEvent toEntity(ChangeEventRequest request) {
        if ( request == null ) {
            return null;
        }

        ChangeEvent.ChangeEventBuilder changeEvent = ChangeEvent.builder();

        changeEvent.schemaName( request.getSchemaName() );
        changeEvent.tableName( request.getTableName() );
        changeEvent.primaryKeyValue( request.getPrimaryKeyValue() );
        changeEvent.operationType( request.getOperationType() );
        changeEvent.beforeData( request.getBeforeData() );
        changeEvent.afterData( request.getAfterData() );

        return changeEvent.build();
    }

    private UUID changeEventConnectionId(ChangeEvent changeEvent) {
        DatabaseConnection connection = changeEvent.getConnection();
        if ( connection == null ) {
            return null;
        }
        return connection.getId();
    }

    private UUID changeEventTriggerId(ChangeEvent changeEvent) {
        TriggerDefinition trigger = changeEvent.getTrigger();
        if ( trigger == null ) {
            return null;
        }
        return trigger.getId();
    }
}
