package com.cognizant.fse.controller;

import com.cognizant.fse.repository.ParentTaskRespository;
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
public class ParentTaskControllerTest {

    @Autowired
    ParentTaskController parentTaskController;

    @Autowired
    ParentTaskRespository parentTaskRespository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.parentTaskRespository.findAll().forEach(parentTaskRespository::delete);
    }

    @Test
    public void addParentTask_test1() throws Exception {
        String parentTaskRequest = "{\n" +
                                    "    \"name\": \"Parent Task\"\n" +
                                    "}";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/parenttasks")
                                                                        .contentType(MediaType.APPLICATION_JSON)
                                                                        .content(parentTaskRequest);

        this.mockMvc.perform(builder)
                    .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void getParentTask_test1() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/parenttasks");

        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
