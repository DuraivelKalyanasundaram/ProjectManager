package com.cognizant.fse.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String employeeId;
    @OneToMany(mappedBy = "id")
    private Set<Project> projects;
    @OneToMany(mappedBy = "id")
    private Set<Task> tasks;

    public User() {
    }

    public User(Long id, String firstName, String lastName, String employeeId, Set<Project> projects, Set<Task> tasks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeId = employeeId;
        this.projects = projects;
        this.tasks = tasks;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Set<Task> getTasks() {
        return tasks;
    }
}
