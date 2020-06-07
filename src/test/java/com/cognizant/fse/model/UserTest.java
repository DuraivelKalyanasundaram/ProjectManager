package com.cognizant.fse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class UserTest {

    @Test
    public void test1() {
        Project project1 = new Project(1L, "Project1", new Date(), new Date(), 1);
        Project project2 = new Project(2L, "Project2", new Date(), new Date(), 2);
        Task task1 = new Task(1L, null, project1, "Task 1", new Date(), new Date(), 1, TaskStatus.NOT_STARTED);
        Task task2 = new Task(2L, null, project2, "Task 2", new Date(), new Date(), 1, TaskStatus.NOT_STARTED);
        User user = new User(1L, "First name", "Last Name", "123456",
                                new HashSet<>(Arrays.asList(project1, project2)),
                                new HashSet<>(Arrays.asList(task1, task2)));

        Assertions.assertNotNull(user);
        Assertions.assertEquals(2, user.getProjects().size());
        Assertions.assertEquals(2, user.getTasks().size());
    }

}
