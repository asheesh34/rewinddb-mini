package com.rewinddb.rollback.mapper;

import com.rewinddb.rollback.dto.RollbackResponse;
import com.rewinddb.rollback.entity.RollbackOperation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RollbackOperationMapper {
    @Mapping(source = "connection.id", target = "connectionId")
    @Mapping(source = "requestedBy.id", target = "requestedBy")
    RollbackResponse toResponse(RollbackOperation rollbackOperation);
}
