package com.rewinddb.trigger.mapper;

import com.rewinddb.trigger.dto.TriggerRequest;
import com.rewinddb.trigger.dto.TriggerResponse;
import com.rewinddb.trigger.entity.TriggerDefinition;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TriggerDefinitionMapper {
    @Mapping(source = "connection.id", target = "connectionId")
    TriggerResponse toResponse(TriggerDefinition triggerDefinition);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "connection", ignore = true)
    @Mapping(target = "triggerName", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    TriggerDefinition toEntity(TriggerRequest request);
}
