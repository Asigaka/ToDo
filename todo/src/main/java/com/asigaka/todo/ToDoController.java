package com.asigaka.todo;

import com.asigaka.todo.model.ToDo;
import com.asigaka.todo.repository.ToDoRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

public class ToDoController {
    private ToDoRepository repository;
    private String dateFormat = "dd-MM-yyyy HH:mm";

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    public void SaveToDo(String description, String deadline) {
        if (getDateByString(deadline) != null) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            ToDo toDo = new ToDo(description, df.format(creationDate), deadline);
            repository.save(toDo);
        }
    }

    public ToDo SaveAndGetToDo(String description, String deadline) {
        ToDo toDo = null;
        if (getDateByString(deadline) != null) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            toDo = new ToDo(description, df.format(creationDate), deadline);
            repository.save(toDo);
        }
        return toDo;
    }

    private Date getDateByString(String dateStr) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Incorrect date");
        }
        return date;
    }

    public void ShowAllNotReadyTasks() {
        for (ToDo toDo: getTasksFromRepository()) {
            if (!toDo.getReadiness() && toDo.getParentId() == 0) {
                System.out.println(toDo.toString());
            }
        }
    }

    public void ShowAllReadyTasks() {
        for (ToDo toDo: getTasksFromRepository()) {
            if (toDo.getReadiness() && toDo.getParentId() == 0) {
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

    public void ShowAllChildrenByParentId(Long idParent) {
        for (ToDo toDo: getTasksFromRepository()) {
            if (idParent == toDo.getParentId()) {
                System.out.println(toDo.toString());
            }
        }
    }

    public void RunCheckOfDeadline() {
        Runnable task = new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(60);
                    for (ToDo toDo: getTasksFromRepository()) {
                        if (hoursBetween(getDateByString(toDo.getCreationDate())
                                ,getDateByString(toDo.getDeadlineDate())) <= 1) {
                            System.out.println("==========");
                            System.out.println("Deadline will end soon!");
                            System.out.println(toDo.toString());
                            System.out.println("==========");
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread checkThread = new Thread(task);
        checkThread.start();
    }

    private int hoursBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60));
    }

    private Iterable<ToDo> getTasksFromRepository() {
        return repository.findAll();
    }
}
