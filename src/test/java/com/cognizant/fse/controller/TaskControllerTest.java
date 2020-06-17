package com.cognizant.fse.controller;

import com.cognizant.fse.model.ParentTask;
import com.cognizant.fse.model.Project;
import com.cognizant.fse.model.Task;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.ParentTaskRespository;
import com.cognizant.fse.repository.TaskRepository;
import com.cognizant.fse.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Parent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.StandardSocketOptions;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    TaskController taskController;

    @Autowired
    ParentTaskRespository parentTaskRespository;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.taskRepository.findAll().forEach(this.taskRepository::delete);
        this.parentTaskRespository.findAll().forEach(this.parentTaskRespository::delete);
        this.userRepository.findAll().forEach(this.userRepository::delete);
    }

    @Test
    public void addTask_test1() throws Exception {
        String parentTaskRequest = "{\n" +
                                    "    \"name\": \"Parent Task\"\n" +
                                    "}";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/parenttasks")
                                                                        .contentType(MediaType.APPLICATION_JSON)
                                                                        .content(parentTaskRequest);
        this.mockMvc.perform(builder)
                        .andExpect(MockMvcResultMatchers.status().isOk());

        MockHttpServletRequestBuilder getBuilder = MockMvcRequestBuilders.get("/parenttasks");
        MvcResult mvcResult = this.mockMvc.perform(getBuilder).andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        String response = mvcResult.getResponse().getContentAsString();
        List<ParentTask> parentTasks = objectMapper.readValue(response, new TypeReference<List<ParentTask>>(){});

        String userRequest = "{\n" +
                                "    \"firstName\": \"test\",\n" +
                                "    \"lastName\": \"test\",\n" +
                                "    \"employeeId\": \"123456\"\n" +
                                "}";
        MockHttpServletRequestBuilder userPostBuilder = MockMvcRequestBuilders.post("/users")
                                                                            .contentType(MediaType.APPLICATION_JSON)
                                                                            .content(userRequest);

        this.mockMvc.perform(userPostBuilder)
                            .andExpect(MockMvcResultMatchers.status().isOk());

        User retrievedUser = this.userRepository.findAll().get(0);

        String taskRequest = "{\n" +
                            "    \"name\": \"Task 1\",\n" +
                            "    \"parentTask\": {\n" +
                            "        \"id\":\"" + parentTasks.get(0).getId() +"\",\n" +
                            "        \"name\": \"" + parentTasks.get(0).getName() + "\"\n" +
                            "    },\n" +
                            "    \"startDate\": \"2020-06-17\",\n" +
                            "    \"endDate\": \"2020-12-31\",\n" +
                            "    \"priority\": \"1\",\n" +
                            "    \"status\": \"NOT_STARTED\",\n" +
                            "    \"user\": {\n" +
                            "        \"id\": \"" + retrievedUser.getId() + "\",\n" +
                            "        \"firstName\": \"" + retrievedUser.getFirstName() + "\",\n" +
                            "        \"lastName\": \"" + retrievedUser.getLastName() + "\",\n" +
                            "        \"employeeId\": \"" + retrievedUser.getEmployeeId() + "\"\n" +
                            "    }\n" +
                            "}";
        MockHttpServletRequestBuilder taskPostRequestBuilder = MockMvcRequestBuilders.post("/tasks")
                                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                                    .content(taskRequest);
        this.mockMvc.perform(taskPostRequestBuilder)
                    .andExpect(MockMvcResultMatchers.status().isOk());


        MockHttpServletRequestBuilder taskGetRequestBuilder = MockMvcRequestBuilders.get("/tasks");
        MvcResult result = this.mockMvc.perform(taskGetRequestBuilder)
                                        .andExpect(MockMvcResultMatchers.status().isOk())
                                        .andReturn();
        response = result.getResponse().getContentAsString();
        List<Task> tasks = objectMapper.readValue(response, new TypeReference<List<Task>>(){});
        Assertions.assertEquals(1, tasks.size());

    }

}
