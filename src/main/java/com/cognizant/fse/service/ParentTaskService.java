package com.cognizant.fse.service;

import com.cognizant.fse.model.ParentTask;

import java.util.List;

public interface ParentTaskService {

    ParentTask addParentTask(ParentTask parentTask);

    List<ParentTask> getParentTasks();

}
