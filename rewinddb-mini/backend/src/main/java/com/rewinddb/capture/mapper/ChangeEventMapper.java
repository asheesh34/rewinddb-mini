package com.rewinddb.capture.mapper;

import com.rewinddb.capture.dto.ChangeEventRequest;
import com.rewinddb.capture.dto.ChangeEventResponse;
import com.rewinddb.capture.entity.ChangeEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ChangeEventMapper {
    @Mapping(source = "connection.id", target = "connectionId")
    @Mapping(source = "trigger.id", target = "triggerId")
    ChangeEventResponse toResponse(ChangeEvent changeEvent);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "connection", ignore = true)
    @Mapping(target = "trigger", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "capturedAt", ignore = true)
    ChangeEvent toEntity(ChangeEventRequest request);
}
