package com.cognizant.fse.repository;

import com.cognizant.fse.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmployeeId(String employeeId);
}
