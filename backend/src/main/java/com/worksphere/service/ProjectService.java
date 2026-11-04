package com.worksphere.service;

import com.worksphere.api.dto.Project;
import com.worksphere.api.dto.ProjectRequest;
import com.worksphere.exception.ResourceNotFoundException;
import com.worksphere.mapper.ProjectMapper;
import com.worksphere.repository.EmployeeRepository;
import com.worksphere.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final EmployeeRepository employeeRepository;
    private final ProjectMapper projectMapper;

    @Transactional(readOnly = true)
    public List<Project> getProjects(Long ownerId, String status) {
        List<com.worksphere.entity.Project> projects;
        if (ownerId != null && status != null) {
            projects = projectRepository.findByOwnerIdAndStatus(ownerId, status);
        } else if (ownerId != null) {
            projects = projectRepository.findByOwnerId(ownerId);
        } else if (status != null) {
            projects = projectRepository.findByStatus(status);
        } else {
            projects = projectRepository.findAll();
        }
        return projects.stream().map(projectMapper::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .map(projectMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
    }

    @Transactional
    public Project createProject(ProjectRequest request) {
        com.worksphere.entity.Project project = projectMapper.toEntity(request);
        project.setOwner(employeeRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " + request.getOwnerId())));
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Transactional
    public Project updateProject(Long id, ProjectRequest request) {
        com.worksphere.entity.Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + id));
        projectMapper.updateEntity(request, project);
        project.setOwner(employeeRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found with id " + request.getOwnerId())));
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Transactional
    public void addTeamMember(Long projectId, Long employeeId) {
        com.worksphere.entity.Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        com.worksphere.entity.Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
        
        project.getTeamMembers().add(employee);
        projectRepository.save(project);
    }

    @Transactional
    public void removeTeamMember(Long projectId, Long employeeId) {
        com.worksphere.entity.Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id " + projectId));
        com.worksphere.entity.Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + employeeId));
        
        project.getTeamMembers().remove(employee);
        projectRepository.save(project);
    }
}
