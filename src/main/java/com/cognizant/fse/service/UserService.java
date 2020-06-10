package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;

import java.util.List;

public interface UserService {
    User addUser (User user) throws EmployeeExistsException;
    List<User> getUsers();
    User updateUser (User user);
}
