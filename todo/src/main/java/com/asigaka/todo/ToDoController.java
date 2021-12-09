package com.asigaka.todo;

import com.asigaka.todo.model.ToDo;
import com.asigaka.todo.repository.ToDoRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ToDoController {
    private ToDoRepository repository;
    private String dateFormat = "dd-MM-yyyy";

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    public void SaveToDo(String description, String deadline) {
        if (DateValidCheck(deadline)) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            ToDo toDo = new ToDo(description, df.format(creationDate), deadline, false);
            repository.save(toDo);
        }
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
}
