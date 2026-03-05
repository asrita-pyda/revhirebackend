package org.example.revhire.mapper;

import org.example.revhire.dto.response.JobResponse;
import org.example.revhire.model.Job;
import org.example.revhire.model.JobSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface JobMapper {

    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    @Mapping(source = "employer.id", target = "employerId")
    @Mapping(source = "employer.name", target = "employerName")
    @Mapping(target = "companyName", ignore = true)
    @Mapping(target = "viewCount", expression = "java(job.getJobViews() != null ? job.getJobViews().size() : 0)")
    @Mapping(source = "jobSkills", target = "skills", qualifiedByName = "mapSkills")
    @Mapping(source = "postedAt", target = "postedAt")
    JobResponse toDto(Job job);

    @Named("mapSkills")
    default List<String> mapSkills(List<JobSkill> jobSkills) {
        if (jobSkills == null)
            return null;
        return jobSkills.stream().map(JobSkill::getSkill).collect(Collectors.toList());
    }

    @Mapping(target = "jobViews", ignore = true)
    @Mapping(target = "jobSkills", ignore = true)
    @Mapping(target = "employer", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Job toEntity(JobResponse jobResponse);
}
