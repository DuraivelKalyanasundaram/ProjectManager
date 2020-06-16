package com.cognizant.fse.repository;

import com.cognizant.fse.model.ParentTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParentTaskRespository extends JpaRepository<ParentTask, Long> {
}
