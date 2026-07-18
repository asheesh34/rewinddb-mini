package com.rewinddb.audit.mapper;

import com.rewinddb.audit.dto.AuditLogRequest;
import com.rewinddb.audit.dto.AuditLogResponse;
import com.rewinddb.audit.entity.AuditLog;
import com.rewinddb.auth.entity.AppUser;
import java.util.UUID;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class AuditLogMapperImpl implements AuditLogMapper {

    @Override
    public AuditLogResponse toResponse(AuditLog auditLog) {
        if ( auditLog == null ) {
            return null;
        }

        AuditLogResponse.AuditLogResponseBuilder auditLogResponse = AuditLogResponse.builder();

        auditLogResponse.userId( auditLogUserId( auditLog ) );
        auditLogResponse.id( auditLog.getId() );
        auditLogResponse.action( auditLog.getAction() );
        auditLogResponse.resourceType( auditLog.getResourceType() );
        auditLogResponse.resourceId( auditLog.getResourceId() );
        auditLogResponse.metadata( auditLog.getMetadata() );
        auditLogResponse.ipAddress( auditLog.getIpAddress() );
        auditLogResponse.createdAt( auditLog.getCreatedAt() );

        return auditLogResponse.build();
    }

    @Override
    public AuditLog toEntity(AuditLogRequest request) {
        if ( request == null ) {
            return null;
        }

        AuditLog.AuditLogBuilder auditLog = AuditLog.builder();

        auditLog.action( request.getAction() );
        auditLog.resourceType( request.getResourceType() );
        auditLog.resourceId( request.getResourceId() );
        auditLog.metadata( request.getMetadata() );
        auditLog.ipAddress( request.getIpAddress() );

        return auditLog.build();
    }

    private UUID auditLogUserId(AuditLog auditLog) {
        AppUser user = auditLog.getUser();
        if ( user == null ) {
            return null;
        }
        return user.getId();
    }
}
