package com.asigaka.todo.model;

import javax.persistence.*;

@Entity
public class ToDo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String description;
    private String creationDate;
    private String deadlineDate;
    private boolean isReady = false;

    /**
     * ID родительской задачи. Если {@link #parentId} равна 0, то родтельской задачи у этой задачи нет
     */
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

    public boolean getReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
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
        if (isReady) {
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
