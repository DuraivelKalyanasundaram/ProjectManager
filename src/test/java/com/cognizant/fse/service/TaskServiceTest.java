package com.cognizant.fse.service;

import com.cognizant.fse.model.ParentTask;
import com.cognizant.fse.model.Task;
import com.cognizant.fse.model.TaskStatus;
import com.cognizant.fse.repository.TaskRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class TaskServiceTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    ParentTaskService parentTaskService;

    @BeforeEach
    public void setup() {
        this.taskRepository.findAll().forEach(this.taskRepository::delete);
    }

    @Test
    public void addTask_test1() {
        Assertions.assertNull(this.taskService.addTask(null));
    }

    @Test
    public void addTask_test2() throws Exception {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask persistedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(persistedParentTask);

        Task task = new Task(1L, persistedParentTask, "Task 1", new Date(),
                            new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                1, TaskStatus.NOT_STARTED, null, null);
        Task persistedTask = this.taskService.addTask(task);
        Assertions.assertNotNull(persistedTask);
        Assertions.assertEquals("Task 1", persistedTask.getName());
        Assertions.assertEquals(1, persistedTask.getPriority());
        Assertions.assertEquals(TaskStatus.NOT_STARTED, persistedTask.getStatus());

    }

    @Test
    public void addTask_test3() throws Exception {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask persistedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(persistedParentTask);

        Task task1 = new Task(1L, persistedParentTask, "Task 1", new Date(),
                                new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                1, TaskStatus.NOT_STARTED, null, null);
        Task task2 = new Task(2L, persistedParentTask, "Task 2", new Date(),
                                new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                2, TaskStatus.NOT_STARTED, null, null);
        Task persistedTask1 = this.taskService.addTask(task1);
        Task persistedTask2 = this.taskService.addTask(task2);

        Assertions.assertNotNull(persistedTask1);
        Assertions.assertEquals("Task 1", persistedTask1.getName());
        Assertions.assertEquals(1, persistedTask1.getPriority());
        Assertions.assertEquals(TaskStatus.NOT_STARTED, persistedTask1.getStatus());

        Assertions.assertNotNull(persistedTask2);
        Assertions.assertEquals("Task 2", persistedTask2.getName());
        Assertions.assertEquals(2, persistedTask2.getPriority());
        Assertions.assertEquals(TaskStatus.NOT_STARTED, persistedTask2.getStatus());
    }

    @Test
    public void getTasks_test1() throws Exception {
        Assertions.assertEquals(0, this.taskService.getTasks().size());
    }

    @Test
    public void getTasks_test2() throws Exception {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask persistedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(persistedParentTask);

        Task task = new Task(1L, persistedParentTask, "Task 1", new Date(),
                                new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                1, TaskStatus.NOT_STARTED, null, null);
        this.taskService.addTask(task);
        Assertions.assertEquals(1, this.taskService.getTasks().size());
    }

    @Test
    public void getTasks_test3() throws Exception {
        ParentTask parentTask = new ParentTask(1L, "Parent Task", null);
        ParentTask persistedParentTask = this.parentTaskService.addParentTask(parentTask);
        Assertions.assertNotNull(persistedParentTask);

        Task task1 = new Task(1L, persistedParentTask, "Task 1", new Date(),
                                new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                1, TaskStatus.NOT_STARTED, null, null);
        Task task2 = new Task(2L, persistedParentTask, "Task 2", new Date(),
                                new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                2, TaskStatus.NOT_STARTED, null, null);
        this.taskService.addTask(task1);
        this.taskService.addTask(task2);

        Assertions.assertEquals(2, this.taskService.getTasks().size());
    }

}
