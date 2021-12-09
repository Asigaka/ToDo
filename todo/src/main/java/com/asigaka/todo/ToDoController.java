package com.asigaka.todo;

import com.asigaka.todo.model.ToDo;
import com.asigaka.todo.repository.ToDoRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoController {
    private ToDoRepository repository;
    private String dateFormat = "dd-MM-yyyy HH:mm";

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    public void SaveToDo(String description, String deadline) {
        if (DateValidCheck(deadline)) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            ToDo toDo = new ToDo(description, df.format(creationDate), deadline);
            repository.save(toDo);
        }
    }

    public ToDo SaveAndGetToDo(String description, String deadline) {
        ToDo toDo = null;
        if (DateValidCheck(deadline)) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            toDo = new ToDo(description, df.format(creationDate), deadline);
            repository.save(toDo);
        }
        return toDo;
    }

    public boolean DateValidCheck(String dateStr) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = df.parse(dateStr);
            return true;
        } catch (ParseException e) {
            System.out.println("Incorrect date");
            return false;
        }
    }

    public void ShowAllNotReadyTasks() {
        for (ToDo toDo: getTasksFromRepository()) {
            if (!toDo.getReadiness()) {
                System.out.println(toDo.toString());
            }
        }
    }

    public void ShowAllReadyTasks() {
        for (ToDo toDo: getTasksFromRepository()) {
            if (toDo.getReadiness()) {
                System.out.println(toDo.toString());
            }
        }
    }

    public void CompleteTaskById(Long id) {
        ToDo toDo = getTaskById(id);
        if (toDo != null) {
            toDo.setReadiness(true);
            repository.save(toDo);
        }
    }

    public void AddChildTaskToParentById(Long parentId, String childDescription, String deadline) {
        ToDo parent = getTaskById(parentId);
        ToDo child = SaveAndGetToDo(childDescription, deadline);
        if (parent != null && child != null) {
            child.setParentId(parent.getId());
            repository.save(parent);
            repository.save(child);
        }
    }

    private ToDo getTaskById(Long id) {
        for (ToDo toDo: getTasksFromRepository()) {
            if (toDo.getId() == id) {
                return toDo;
            }
        }
        System.out.println("There is no task with such a id");
        return null;
    }

    private Iterable<ToDo> getTasksFromRepository() {
        return repository.findAll();
    }
}
