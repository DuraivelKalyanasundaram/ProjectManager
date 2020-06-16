package com.cognizant.fse.service;

import com.cognizant.fse.model.ParentTask;
import com.cognizant.fse.repository.ParentTaskRespository;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Service
public class ParentTaskServiceImpl implements ParentTaskService{

    @Autowired
    ParentTaskRespository parentTaskRespository;

    private static final Logger logger = LogManager.getLogger(ParentTaskServiceImpl.class);

    @Override
    public ParentTask addParentTask(ParentTask parentTask) {
        if (parentTask != null) {
            ParentTask savedParentTask = parentTaskRespository.save(parentTask);
            if (savedParentTask != null) {
                return savedParentTask;
            }
        }
        return null;
    }

    @Override
    public List<ParentTask> getParentTasks() {
        return this.parentTaskRespository.findAll();
    }
}
