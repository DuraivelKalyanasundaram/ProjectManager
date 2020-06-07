package com.cognizant.fse.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.time.format.DateTimeFormatter;


public class ProjectTest {

    @Test
    public void test1() {
        Project project = new Project(1L, "sample project", new Date(new java.util.Date().getTime()), new Date(new java.util.Date().getTime()), 1);
        Assertions.assertEquals("sample project", project.getName());
        Assertions.assertEquals(1, project.getId());
        Assertions.assertEquals(1, project.getPriority());
        Assertions.assertNotNull(project.getStartDate());
        Assertions.assertNotNull(project.getEndDate());
    }

}
