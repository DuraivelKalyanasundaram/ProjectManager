package com.cognizant.fse.service;

import com.cognizant.fse.model.ParentTask;
import com.cognizant.fse.repository.ParentTaskRespository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ParentTaskServiceTest {

    @Autowired
    ParentTaskRespository parentTaskRespository;

    @Autowired
    ParentTaskService parentTaskService;

    @BeforeEach
    public void setup() {
        this.parentTaskRespository.findAll().forEach(parentTaskRespository::delete);
    }

    @Test
    public void addParentTask_test1() {
        Assertions.assertNull(this.parentTaskService.addParentTask(null));
    }

    @Test
    public void addParentTask_test2() {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask savedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(savedParentTask);
        Assertions.assertEquals("Parent Task", savedParentTask.getName());
    }

    @Test
    public void getParentTasks_test1() {
        Assertions.assertEquals(0, this.parentTaskService.getParentTasks().size());
    }

    @Test
    public void getParentTasks_test2() {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask savedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(savedParentTask);
        Assertions.assertEquals("Parent Task", savedParentTask.getName());

        Assertions.assertEquals(1, this.parentTaskService.getParentTasks().size());
    }

    @Test
    public void getParentTasks_test3() {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask parentTask1 = new ParentTask(2L, "Parent Task 2", null);
        this.parentTaskService.addParentTask(parentTask);
        this.parentTaskService.addParentTask(parentTask1);

        Assertions.assertEquals(2, this.parentTaskService.getParentTasks().size());
    }


}
