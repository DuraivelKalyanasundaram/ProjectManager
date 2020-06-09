package com.cognizant.fse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class TaskTest {

    @Test
    public void test1() {
        Project project = new Project(1L, "Project 1", new Date(), new Date(), 1);
        Task task = new Task(1L, null, new HashSet<>(Arrays.asList(project)), "Task1", new Date(), new Date(), 1, TaskStatus.NOT_STARTED);

        Assertions.assertNotNull(task);
        Assertions.assertNull(task.getParentTask());
        Assertions.assertEquals(project, task.getProjects().toArray()[0]);
        Assertions.assertEquals(1, task.getId());
        Assertions.assertEquals("Task1", task.getName());
        Assertions.assertEquals(1, task.getPriority());
        Assertions.assertEquals("NOT_STARTED", task.getStatus().name());
    }

    @Test
    public void test2() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task1");
        task.setPriority(1);
        task.setStatus(TaskStatus.NOT_STARTED);

        Assertions.assertNotNull(task);
        Assertions.assertNull(task.getParentTask());
        Assertions.assertEquals(1, task.getId());
        Assertions.assertEquals("Task1", task.getName());
        Assertions.assertEquals(1, task.getPriority());
        Assertions.assertEquals("NOT_STARTED", task.getStatus().name());
    }

    @Test
    public void test3() {
        ParentTask parentTask = new ParentTask(1L, "Parent 1");
        Project project = new Project(1L, "Project 1", new Date(), new Date(), 1);
        Task task1 = new Task(1L, parentTask, new HashSet<>(Arrays.asList(project)), "Task 1", new Date(), new Date(), 1, TaskStatus.NOT_STARTED);
        Task task2 = new Task(2L, parentTask, new HashSet<>(Arrays.asList(project)), "Task 2", new Date(), new Date(), 2, TaskStatus.NOT_STARTED);
        Assertions.assertNotNull(parentTask);
        Assertions.assertNotNull(task1);
        Assertions.assertNotNull(task2);
        Assertions.assertEquals(parentTask, task1.getParentTask());
        Assertions.assertEquals(project, task1.getProjects().toArray()[0]);
        Assertions.assertEquals(parentTask, task2.getParentTask());
        Assertions.assertEquals(project, task2.getProjects().toArray()[0]);
    }

}
