package com.flowlog.repository;

import com.flowlog.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    List<Project> findAllActive();

    @Query("SELECT p FROM Project p WHERE p.teamName = :teamName AND p.deletedAt IS NULL ORDER BY p.createdAt DESC")
    List<Project> findByTeamName(String teamName);
}
