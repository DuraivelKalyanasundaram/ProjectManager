package com.cognizant.fse.service;

import com.cognizant.fse.model.Task;

import java.util.List;

public interface TaskService {
    Task addTask(Task task);
    List<Task> getTasks();
    List<Task> getTasks(Long projectId);
    Task updateTask(Task task);
}
