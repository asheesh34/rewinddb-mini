package com.rewinddb.history.mapper;

import com.rewinddb.capture.entity.ChangeEvent;
import com.rewinddb.connection.entity.DatabaseConnection;
import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.entity.HistoryVersion;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class HistoryVersionMapperImpl implements HistoryVersionMapper {

    @Override
    public HistoryVersionResponse toResponse(HistoryVersion historyVersion) {
        if ( historyVersion == null ) {
            return null;
        }

        HistoryVersionResponse.HistoryVersionResponseBuilder historyVersionResponse = HistoryVersionResponse.builder();

        historyVersionResponse.connectionId( historyVersionConnectionId( historyVersion ) );
        historyVersionResponse.changeEventId( historyVersionChangeEventId( historyVersion ) );
        historyVersionResponse.id( historyVersion.getId() );
        historyVersionResponse.versionNumber( historyVersion.getVersionNumber() );
        historyVersionResponse.schemaName( historyVersion.getSchemaName() );
        historyVersionResponse.tableName( historyVersion.getTableName() );
        historyVersionResponse.rowSnapshot( historyVersion.getRowSnapshot() );
        historyVersionResponse.createdAt( historyVersion.getCreatedAt() );

        return historyVersionResponse.build();
    }

    private UUID historyVersionConnectionId(HistoryVersion historyVersion) {
        DatabaseConnection connection = historyVersion.getConnection();
        if ( connection == null ) {
            return null;
        }
        return connection.getId();
    }

    private UUID historyVersionChangeEventId(HistoryVersion historyVersion) {
        ChangeEvent changeEvent = historyVersion.getChangeEvent();
        if ( changeEvent == null ) {
            return null;
        }
        return changeEvent.getId();
    }
}
