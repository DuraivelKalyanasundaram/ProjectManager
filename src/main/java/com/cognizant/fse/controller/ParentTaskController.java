package com.cognizant.fse.controller;

import com.cognizant.fse.model.ParentTask;
import com.cognizant.fse.service.ParentTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parenttasks")
public class ParentTaskController {

    @Autowired
    ParentTaskService parentTaskService;

    @PostMapping
    public ResponseEntity addParentTask(@RequestBody ParentTask parentTask) {
        ParentTask savedParentTask = this.parentTaskService.addParentTask(parentTask);
        if (savedParentTask != null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public List<ParentTask> getParentTasks() {
        return this.parentTaskService.getParentTasks();
    }

}
