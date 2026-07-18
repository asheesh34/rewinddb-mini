package com.rewinddb.auth.mapper;

import com.rewinddb.auth.dto.UserResponse;
import com.rewinddb.auth.entity.AppUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-07-17T11:47:54+0530",
    comments = "version: 1.6.3, compiler: javac, environment: Java 17.0.17 (Oracle Corporation)"
)
@Component
public class AppUserMapperImpl implements AppUserMapper {

    @Override
    public UserResponse toResponse(AppUser user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.email( user.getEmail() );
        userResponse.displayName( user.getDisplayName() );
        userResponse.role( user.getRole() );
        userResponse.enabled( user.isEnabled() );
        userResponse.createdAt( user.getCreatedAt() );
        userResponse.updatedAt( user.getUpdatedAt() );

        return userResponse.build();
    }
}
