package com.cognizant.fse.controller;

import com.cognizant.fse.model.Task;
import com.cognizant.fse.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private static final Logger logger = LogManager.getLogger(TaskController.class);
    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity addTask(@RequestBody Task task) {
        Task persistedTask = this.taskService.addTask(task);
        if (persistedTask != null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return this.taskService.getTasks();
    }

    @GetMapping("/project/{id}")
    public List<Task> getTasksForProject(@PathVariable("id") Long id) {
        if (id > 0) {
            List<Task> projectTasks = this.taskService.getTasks(id);
            logger.info("Project tasks being returned are " + projectTasks);
            return projectTasks;
        }
        return new ArrayList<>();
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        return this.taskService.updateTask(task);
    }

}
