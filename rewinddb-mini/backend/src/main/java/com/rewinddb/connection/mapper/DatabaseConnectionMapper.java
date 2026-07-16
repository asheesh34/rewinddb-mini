package com.rewinddb.connection.mapper;

import com.rewinddb.connection.dto.DatabaseConnectionRequest;
import com.rewinddb.connection.dto.DatabaseConnectionResponse;
import com.rewinddb.connection.entity.DatabaseConnection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DatabaseConnectionMapper {
    DatabaseConnectionResponse toResponse(DatabaseConnection connection);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "encryptedPassword", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DatabaseConnection toEntity(DatabaseConnectionRequest request);
}
