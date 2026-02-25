package org.example.revhire.mapper;

import org.example.revhire.dto.response.SavedJobResponse;
import org.example.revhire.model.SavedJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SavedJobMapper {
    SavedJobMapper INSTANCE = Mappers.getMapper(SavedJobMapper.class);

    @Mapping(source = "job.id", target = "jobId")
    @Mapping(source = "job.title", target = "jobTitle")
    @Mapping(target = "companyName", ignore = true)
    @Mapping(source = "savedAt", target = "savedAt")
    SavedJobResponse toDto(SavedJob savedJob);
}
