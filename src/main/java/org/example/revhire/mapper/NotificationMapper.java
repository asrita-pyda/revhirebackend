package org.example.revhire.mapper;


import org.example.revhire.dto.response.NotificationResponse;
import org.example.revhire.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(source = "createdAt", target = "createdAt")
    NotificationResponse toDto(Notification notification);
}

