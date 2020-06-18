package com.cognizant.fse.service;

import com.cognizant.fse.model.Task;
import com.cognizant.fse.model.TaskStatus;
import com.cognizant.fse.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Task> getTasks(Long projectId) {
        return this.taskRepository.findByProjectId(projectId);
    }

    @Override
    public Task updateTask(Task task) {
        if (task != null) {
            Optional<Task> persistedTaskOptional = this.taskRepository.findById(task.getId());
            if (persistedTaskOptional.isPresent()) {
                Task persistedTask = persistedTaskOptional.get();
                persistedTask.setName(task.getName());
                persistedTask.setStatus(task.getStatus());
                persistedTask.setStartDate(task.getStartDate());
                persistedTask.setEndDate(task.getEndDate());
                persistedTask.setPriority(task.getPriority());
                persistedTask.setParentTask(task.getParentTask());
                persistedTask.setUser(task.getUser());
                persistedTask.setProject(task.getProject());
                Task updatedTask = this.taskRepository.save(persistedTask);
                if (updatedTask != null) {
                    return  updatedTask;
                }
            }
        }
        return null;
    }
}
