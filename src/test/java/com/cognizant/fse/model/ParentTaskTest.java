package com.cognizant.fse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

public class ParentTaskTest {

    @Test
    public void test1() {
        ParentTask parentTask = new ParentTask(1L, "Parent 1");
        Assertions.assertNotNull(parentTask);
        Assertions.assertEquals(1, parentTask.getId());
        Assertions.assertEquals("Parent 1", parentTask.getName());
    }

    @Test
    public void test2() {
        ParentTask parentTask = new ParentTask();
        parentTask.setId(1L);
        parentTask.setName("Parent 1");
        Assertions.assertNotNull(parentTask);
        Assertions.assertEquals(1, parentTask.getId());
        Assertions.assertEquals("Parent 1", parentTask.getName());
    }

}
