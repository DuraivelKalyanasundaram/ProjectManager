package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository.findAll().forEach(userRepository::delete);
    }

    @Test
    public void adduser_test1() throws Exception{
        User user = new User(1L, "name", "name", "123456", new HashSet<>(), new HashSet<>());
        User returnedUser = userService.addUser(user);
        Assertions.assertNotNull(returnedUser);
    }

    @Test
    public void addUser_test2() throws Exception {
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>(), new HashSet<>());
        User user2 = new User(2L, "name", "name", "1234567", new HashSet<>(), new HashSet<>());

        User returnedUser1 = userService.addUser(user1);
        User returnedUser2 = userService.addUser(user2);

        Assertions.assertNotNull(returnedUser1);
        Assertions.assertNotNull(returnedUser2);
    }

    @Test
    public void addUser_test3() throws Exception {
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>(), new HashSet<>());
        User user2 = new User(2L, "name", "name", "123456", new HashSet<>(), new HashSet<>());

        User returnedUser1 = userService.addUser(user1);
        Assertions.assertThrows(EmployeeExistsException.class, () -> {
                    User returnedUser2 = userService.addUser(user2);
                });
        Assertions.assertNotNull(returnedUser1);

    }

    @Test
    public void getUsers_test1() {
        Assertions.assertEquals(0, this.userService.getUsers().size());
    }

    @Test
    public void getUsers_test2() throws Exception{
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>(), new HashSet<>());
        userService.addUser(user1);
        Assertions.assertEquals(1, this.userService.getUsers().size());
    }

    @Test
    public void getUsers_test3() throws Exception {
        User user1 = new User(1L, "name", "name", "123456", new HashSet<>(), new HashSet<>());
        User user2 = new User(2L, "name", "name", "1234567", new HashSet<>(), new HashSet<>());

        User returnedUser1 = userService.addUser(user1);
        User returnedUser2 = userService.addUser(user2);

        Assertions.assertEquals(2, this.userService.getUsers().size());
    }

}
