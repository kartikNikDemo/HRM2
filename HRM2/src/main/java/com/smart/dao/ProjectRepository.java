package com.smart.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.smart.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	 @Query("SELECT p FROM Project p")
	  List<Project> getAllProjectList();
}
