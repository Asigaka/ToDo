package com.asigaka.todo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private String creationDate;
    private String deadlineDate;
    private boolean isReadiness = false;

    private long parentId;

    public ToDo() {
    }

    public ToDo(String description, String creationDate, String deadlineDate) {
        this.description = description;
        this.creationDate = creationDate;
        this.deadlineDate = deadlineDate;
    }

    public long getId() {
        return id;
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

    public long getParentId() {
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
                "\nParent id: " + parentId +
                "\nDescription: " + description +
                "\nCreation date: " + creationDate +
                "\nDeadline: " + deadlineDate +
                "\n" + readyStr +
                "\n------------------";
    }
}
