package com.cognizant.fse.controller;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;
import com.cognizant.fse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    @PutMapping("/{id}")
    public ResponseEntity updateuser (@PathVariable ("id") Long id, @RequestBody User user) {
        User persistedUser = this.userService.updateUser(user);
        if (persistedUser != null) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser (@PathVariable("id") Long id) {
        this.userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
