package com.worksphere.controller;

import com.worksphere.api.ProjectsApi;
import com.worksphere.api.dto.AddTeamMemberRequest;
import com.worksphere.api.dto.Project;
import com.worksphere.api.dto.ProjectRequest;
import com.worksphere.api.dto.ProjectStatus;
import com.worksphere.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectsApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<Project> createProject(ProjectRequest projectRequest) {
        return new ResponseEntity<>(projectService.createProject(projectRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Project> getProject(Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @Override
    public ResponseEntity<List<Project>> listProjects(Long ownerId, ProjectStatus status) {
        String statusStr = status != null ? status.getValue() : null;
        return ResponseEntity.ok(projectService.getProjects(ownerId, statusStr));
    }

    @Override
    public ResponseEntity<Project> updateProject(Long id, ProjectRequest projectRequest) {
        return ResponseEntity.ok(projectService.updateProject(id, projectRequest));
    }

    @Override
    public ResponseEntity<Void> addTeamMember(Long id, AddTeamMemberRequest addTeamMemberRequest) {
        projectService.addTeamMember(id, addTeamMemberRequest.getEmployeeId());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> removeTeamMember(Long id, Long employeeId) {
        projectService.removeTeamMember(id, employeeId);
        return ResponseEntity.noContent().build();
    }
}
