package com.rewinddb.auth.mapper;

import com.rewinddb.auth.dto.UserResponse;
import com.rewinddb.auth.entity.AppUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AppUserMapper {
    UserResponse toResponse(AppUser user);
}
