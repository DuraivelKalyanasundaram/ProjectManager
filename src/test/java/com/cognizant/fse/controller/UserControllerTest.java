package com.cognizant.fse.controller;

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
public class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        userRepository.findAll().forEach(userRepository::delete);
    }

    @Test
    public void getUsers_test1() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/users");
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addUser_test1() throws Exception {
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
    }

    @Test
    public void addUser_test2() throws Exception {
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
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status().isConflict());

    }

}
