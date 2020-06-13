package com.cognizant.fse.service;

import com.cognizant.fse.model.Project;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.ProjectRepository;
import com.cognizant.fse.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.Logger;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;
    private static final Logger logger = LogManager.getLogger(ProjectServiceImpl.class);

    @Override
    public Project addProject(Project project) {
        if(project == null) {
            return null;
        }
        Project persistedProject = projectRepository.save(project);
        return persistedProject;
    }

    @Override
    public List<Project> getProjects() {
        List<Project> projects =  this.projectRepository.findAll();
        logger.info("Sending projects " + projects);
        return projects;
    }

    @Override
    public Project updateProject(Project project) {
        if (project != null) {
            Optional<Project> existingProjectOptional = this.projectRepository.findById(project.getId());
            if (existingProjectOptional.isPresent()) {
                Project existingProject = existingProjectOptional.get();
                existingProject.setName(project.getName());
                existingProject.setStartDate(project.getStartDate());
                existingProject.setEndDate(project.getEndDate());
                existingProject.setPriority(project.getPriority());
                existingProject.setManager(project.getManager());
                return this.projectRepository.save(existingProject);
            }
        }
        return null;
    }
}
