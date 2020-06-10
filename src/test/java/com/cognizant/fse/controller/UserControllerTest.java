package com.cognizant.fse.controller;

import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void updateUser_test1() throws Exception{
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

        String updatedRequest = "{\n" +
                                "    \"id\": " + retrievedUser.getId() + ",\n" +
                                "    \"firstName\": \"test1\",\n" +
                                "    \"lastName\": \"test1\",\n" +
                                "    \"employeeId\": \"123457\"\n" +
                                "}";
        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.put("/users/" + retrievedUser.getId() )
                                                                    .contentType(MediaType.APPLICATION_JSON)
                                                                    .content(updatedRequest);
        this.mockMvc.perform(builder2)
                    .andExpect(MockMvcResultMatchers.status().isOk());

        retrievedUser = this.userRepository.findAll().get(0);
        Assertions.assertEquals("test1", retrievedUser.getFirstName());
        Assertions.assertEquals("test1", retrievedUser.getLastName());
        Assertions.assertEquals("123457", retrievedUser.getEmployeeId());
    }

    @Test
    public void deleteuser_test1() throws Exception {
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

        MockHttpServletRequestBuilder builder2 = MockMvcRequestBuilders.delete("/users/" + retrievedUser.getId());
        this.mockMvc.perform(builder2)
                    .andExpect(MockMvcResultMatchers.status().isOk());

    }

}
