package com.cognizant.fse.controller;

import com.cognizant.fse.model.Project;
import com.cognizant.fse.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> addProject(@RequestBody Project project){
        Project persistedProject = projectService.addProject(project);
        if (persistedProject != null) {
            return new ResponseEntity<Project>(persistedProject, HttpStatus.OK);
        }
        return new ResponseEntity<Project>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<Project> getProjects() {
        return this.projectService.getProjects();
    }

}
