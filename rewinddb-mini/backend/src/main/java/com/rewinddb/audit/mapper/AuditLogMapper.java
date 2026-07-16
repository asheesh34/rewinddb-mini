package com.rewinddb.audit.mapper;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.dto.AuditLogResponse;
import com.rewinddb.audit.entity.AuditLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuditLogMapper {
    @Mapping(source = "user.id", target = "userId")
    AuditLogResponse toResponse(AuditLog auditLog);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AuditLog toEntity(AuditLogRequest request);
}
