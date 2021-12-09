package com.asigaka.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String creationDate;
    private String deadlineDate;
    private boolean isReadiness = false;

    private Long parentId;

    public ToDo() {
    }

    public ToDo(String description, String creationDate, String deadlineDate) {
        this.description = description;
        this.creationDate = creationDate;
        this.deadlineDate = deadlineDate;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public boolean getReadiness() {
        return isReadiness;
    }

    public void setReadiness(boolean readiness) {
        isReadiness = readiness;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        String readyStr = "";
        if (isReadiness) {
            readyStr = "Ready";
        } else {
            readyStr = "Not ready";
        }
        return  "------------------" +
                "\nId: " + id +
                "\nDescription: " + description +
                "\nCreation date: " + creationDate +
                "\nDeadline: " + deadlineDate +
                "\n" + readyStr +
                "\n------------------";
    }
}
