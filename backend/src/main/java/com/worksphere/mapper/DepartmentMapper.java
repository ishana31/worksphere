package com.worksphere.mapper;

import com.worksphere.api.dto.Department;
import com.worksphere.api.dto.DepartmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {

    @Mapping(source = "head.id", target = "headId")
    Department toDto(com.worksphere.entity.Department entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "head", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    com.worksphere.entity.Department toEntity(DepartmentRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "head", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(DepartmentRequest dto, @MappingTarget com.worksphere.entity.Department entity);
}
