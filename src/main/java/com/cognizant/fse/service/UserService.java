package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;

public interface UserService {
    User addUser (User user) throws EmployeeExistsException;
}
