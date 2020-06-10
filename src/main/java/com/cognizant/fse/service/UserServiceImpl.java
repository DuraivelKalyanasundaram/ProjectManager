package com.cognizant.fse.service;

import com.cognizant.fse.exception.EmployeeExistsException;
import com.cognizant.fse.model.User;
import com.cognizant.fse.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);
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

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(User user) {
        logger.info("Beginning to update user - " + user);
        Optional<User> retrievedUserOptional = this.userRepository.findById(user.getId());
        if (retrievedUserOptional.isPresent()) {
            User retrievedUser = retrievedUserOptional.get();
            logger.info("User retrieved for id " + user.getId() + " is " + retrievedUser);
            retrievedUser.setEmployeeId(user.getEmployeeId());
            retrievedUser.setFirstName(user.getFirstName());
            retrievedUser.setLastName(user.getLastName());
            return this.userRepository.save(retrievedUser);
        } else {
            logger.error("Could not retrieve user for id " + user.getId());
            return null;
        }
    }

    @Override
    public void deleteUser(Long id) {
        logger.info("Beginning to delete user id " + id);
        Optional<User> retrievedUserOptional = this.userRepository.findById(id);
        if (retrievedUserOptional.isPresent()) {
            User retrievedUser = retrievedUserOptional.get();
            logger.info("User retrieved for id " + id + " is " + retrievedUser);
            this.userRepository.deleteById(id);
        }
    }
}
