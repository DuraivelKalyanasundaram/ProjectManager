package com.cognizant.fse.controller;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;
import com.cognizant.fse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity addUser(@RequestBody User user){
        try {
            User persistedUser = userService.addUser(user);
            if (persistedUser != null) {
                return new ResponseEntity(HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }catch (EmployeeExistsException ex) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

}
