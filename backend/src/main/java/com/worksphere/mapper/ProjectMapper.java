package com.worksphere.mapper;

import com.worksphere.api.dto.Project;
import com.worksphere.api.dto.ProjectRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProjectMapper {

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(target = "teamMemberIds", expression = "java(entity.getTeamMembers().stream().map(e -> e.getId()).collect(java.util.stream.Collectors.toList()))")
    Project toDto(com.worksphere.entity.Project entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "teamMembers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.worksphere.entity.Project toEntity(ProjectRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "teamMembers", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(ProjectRequest dto, @MappingTarget com.worksphere.entity.Project entity);
}
