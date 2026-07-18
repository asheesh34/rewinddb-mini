package com.rewinddb.connection.mapper;

import com.rewinddb.connection.dto.DatabaseConnectionRequest;
import com.rewinddb.connection.dto.DatabaseConnectionResponse;
import com.rewinddb.connection.entity.DatabaseConnection;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class DatabaseConnectionMapperImpl implements DatabaseConnectionMapper {

    @Override
    public DatabaseConnectionResponse toResponse(DatabaseConnection connection) {
        if ( connection == null ) {
            return null;
        }

        DatabaseConnectionResponse.DatabaseConnectionResponseBuilder databaseConnectionResponse = DatabaseConnectionResponse.builder();

        databaseConnectionResponse.id( connection.getId() );
        databaseConnectionResponse.name( connection.getName() );
        databaseConnectionResponse.host( connection.getHost() );
        databaseConnectionResponse.port( connection.getPort() );
        databaseConnectionResponse.databaseName( connection.getDatabaseName() );
        databaseConnectionResponse.username( connection.getUsername() );
        databaseConnectionResponse.status( connection.getStatus() );
        databaseConnectionResponse.createdAt( connection.getCreatedAt() );
        databaseConnectionResponse.updatedAt( connection.getUpdatedAt() );

        return databaseConnectionResponse.build();
    }

    @Override
    public DatabaseConnection toEntity(DatabaseConnectionRequest request) {
        if ( request == null ) {
            return null;
        }

        DatabaseConnection.DatabaseConnectionBuilder databaseConnection = DatabaseConnection.builder();

        databaseConnection.name( request.getName() );
        databaseConnection.host( request.getHost() );
        databaseConnection.port( request.getPort() );
        databaseConnection.databaseName( request.getDatabaseName() );
        databaseConnection.username( request.getUsername() );

        return databaseConnection.build();
    }
}
