package com.cognizant.fse.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class ParentTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "parentTask", cascade = CascadeType.REMOVE)
//    @JsonBackReference
    @JsonIgnore
    private Set<Task> task;

    public ParentTask() {
    }

    public ParentTask(Long id, String name, Set<Task> task) {
        this.id = id;
        this.name = name;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Task> getTask() {
        return task;
    }

    public void setTask(Set<Task> task) {
        this.task = task;
    }

    @Override
    public String toString() {
        return "ParentTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
