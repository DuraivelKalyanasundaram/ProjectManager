package com.cognizant.fse.service;

import com.cognizant.fse.model.Task;
import com.cognizant.fse.model.TaskStatus;
import com.cognizant.fse.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Override
    public Task addTask(Task task) {
        if (task != null) {
            Task persistedTask = this.taskRepository.save(task);
            if (persistedTask != null) {
                return persistedTask;
            }
        }
        return null;
    }

    @Override
    public List<Task> getTasks() {
        return this.taskRepository.findAll();
    }
}
