package com.rewinddb.history.mapper;

import com.rewinddb.history.dto.HistoryVersionResponse;
import com.rewinddb.history.entity.HistoryVersion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryVersionMapper {
    @Mapping(source = "connection.id", target = "connectionId")
    @Mapping(source = "changeEvent.id", target = "changeEventId")
    HistoryVersionResponse toResponse(HistoryVersion historyVersion);
}
