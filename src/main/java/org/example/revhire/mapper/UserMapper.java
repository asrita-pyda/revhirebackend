package org.example.revhire.mapper;

import org.example.revhire.dto.response.UserResponse;
import org.example.revhire.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "companyName", ignore = true)
    @Mapping(target = "industry", ignore = true)
    @Mapping(target = "companySize", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "website", ignore = true)
    @Mapping(target = "currentStatus", ignore = true)
    @Mapping(target = "totalExperience", ignore = true)
    UserResponse toDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "securityQuestion", ignore = true)
    @Mapping(target = "securityAnswer", ignore = true)
    @Mapping(target = "active", ignore = true)
    User toEntity(UserResponse userResponse);
}
