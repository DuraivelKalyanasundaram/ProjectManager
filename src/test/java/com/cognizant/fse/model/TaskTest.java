package com.cognizant.fse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TaskTest {

    @Test
    public void test1() {
        Task task = new Task(1L, "Task1", new Date(), new Date(), 1, TaskStatus.NOT_STARTED);

        Assertions.assertNotNull(task);
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
        Assertions.assertEquals(1, task.getId());
        Assertions.assertEquals("Task1", task.getName());
        Assertions.assertEquals(1, task.getPriority());
        Assertions.assertEquals("NOT_STARTED", task.getStatus().name());
    }

}
