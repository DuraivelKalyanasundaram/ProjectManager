package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) throws EmployeeExistsException {
        if (userRepository.findByEmployeeId(user.getEmployeeId()) != null) {
            throw new EmployeeExistsException("User already exists - " + user);
        }
        return userRepository.save(user);
    }

}
