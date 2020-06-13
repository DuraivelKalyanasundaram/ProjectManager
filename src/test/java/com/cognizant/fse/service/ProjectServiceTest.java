package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.Project;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.ProjectRepository;
import com.cognizant.fse.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

@SpringBootTest
public class ProjectServiceTest {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setup()
    {
        this.projectRepository.findAll().forEach(projectRepository::delete);
        this.userRepository.findAll().forEach(userRepository::delete);
    }

    @Test
    public void addProject_test1() {
        Assertions.assertNull(this.projectService.addProject(null));
    }

    @Test
    public void addProject_test2() throws ParseException, EmployeeExistsException {
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>());
        User returnedUser1 = userService.addUser(user1);

        Project project = new Project(1L, "New Project", new Date(),
                                        new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                        30,
                                        returnedUser1);
        Project persistedProject = this.projectService.addProject(project);
        Assertions.assertNotNull(persistedProject);
        Assertions.assertEquals("New Project", persistedProject.getName());
        Assertions.assertEquals(new Date().toString(), persistedProject.getStartDate().toString());
        Assertions.assertEquals(30, persistedProject.getPriority());
        Assertions.assertEquals("name", persistedProject.getManager().getFirstName());
        Assertions.assertEquals("name", persistedProject.getManager().getLastName());
        Assertions.assertEquals("123456", persistedProject.getManager().getEmployeeId());

        Assertions.assertEquals(1, this.projectService.getProjects().size());
    }

    @Test
    public void getProjects_test1() {
        Assertions.assertEquals(0, this.projectService.getProjects().size());
    }

    @Test
    public void getProjects_test2() throws EmployeeExistsException, ParseException {
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>());
        User returnedUser1 = userService.addUser(user1);

        Project project = new Project(1L, "New Project", new Date(),
                                        new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                        30,
                                        returnedUser1);
        Project project2 = new Project(2L, "New Project",
                                        new Date(),
                                        new SimpleDateFormat("YYYY-mm-DD").parse("2099-12-31"),
                                        15,
                                        returnedUser1);
        Project persistedProject = this.projectService.addProject(project);
        Project persistedProject2 = this.projectService.addProject(project2);
        Assertions.assertNotNull(persistedProject);
        Assertions.assertNotNull(persistedProject2);
        Assertions.assertEquals(2, this.projectService.getProjects().size());
    }

}
