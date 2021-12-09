package com.asigaka.todo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String description;
    private String creationDate;
    private String deadlineDate;
    private boolean isReadiness;

    public ToDo() {
    }

    public ToDo(String description, String creationDate, String deadlineDate, boolean isReadiness) {
        this.description = description;
        this.creationDate = creationDate;
        this.deadlineDate = deadlineDate;
        this.isReadiness = isReadiness;
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

    @Override
    public String toString() {
        return "ToDo{" +
                "description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", deadlineDate=" + deadlineDate +
                ", isReadiness=" + isReadiness +
                '}';
    }
}
