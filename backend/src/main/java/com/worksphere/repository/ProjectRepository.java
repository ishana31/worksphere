package com.worksphere.repository;

import com.worksphere.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByOwnerId(Long ownerId);
    List<Project> findByStatus(String status);
    List<Project> findByOwnerIdAndStatus(Long ownerId, String status);
}
