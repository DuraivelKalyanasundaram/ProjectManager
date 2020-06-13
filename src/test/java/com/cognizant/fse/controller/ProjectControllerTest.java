package com.cognizant.fse.controller;

import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.ProjectRepository;
import com.cognizant.fse.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProjectControllerTest {
    @Autowired
    ProjectController projectController;

    @Autowired
    UserController userController;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.projectRepository.findAll().forEach(this.projectRepository::delete);
        this.userRepository.findAll().forEach(this.userRepository::delete);
    }

    @Test
    public void addProject_test1() throws Exception{
        String userRequest = "{\n" +
                            "    \"firstName\": \"test\",\n" +
                            "    \"lastName\": \"test\",\n" +
                            "    \"employeeId\": \"123456\"\n" +
                            "}";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/users")
                                                                        .contentType(MediaType.APPLICATION_JSON)
                                                                        .content(userRequest);

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status().isOk());

        User retrievedUser = this.userRepository.findAll().get(0);

        String projectRequest = "{\n" +
                                "    \"name\": \"new project\",\n" +
                                "    \"startDate\": \"2020-06-12\",\n" +
                                "    \"endDate\": \"2020-06-30\",\n" +
                                "    \"priority\": \"30\",\n" +
                                "    \"manager\" : {\n" +
                                "        \"id\": \"" + retrievedUser.getId() + "\",\n" +
                                "        \"firstName\": \"test\",\n" +
                                "        \"lastName\": \"test\",\n" +
                                "        \"employeeId\": \"123456\"\n" +
                                "    }\n" +
                                "}";

        MockHttpServletRequestBuilder projectBuilder = MockMvcRequestBuilders.post("/projects")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(projectRequest);

        this.mockMvc.perform(projectBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk());


    }

}
