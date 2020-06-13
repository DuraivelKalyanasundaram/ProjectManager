package com.cognizant.fse.service;

import com.cognizant.fse.model.Project;

import java.util.List;

public interface ProjectService {
    Project addProject(Project project);
    List<Project> getProjects();
}
