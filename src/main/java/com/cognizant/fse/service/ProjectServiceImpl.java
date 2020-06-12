package com.cognizant.fse.service;

import com.cognizant.fse.model.Project;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.ProjectRepository;
import com.cognizant.fse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project addProject(Project project) {
        if(project == null) {
            return null;
        }
        Project persistedProject = projectRepository.save(project);
        return persistedProject;
    }
}
