package com.cognizant.fse.controller;

import com.cognizant.fse.model.Task;
import com.cognizant.fse.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

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

}
