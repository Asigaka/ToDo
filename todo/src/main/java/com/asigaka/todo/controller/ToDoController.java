package com.asigaka.todo.controller;

import com.asigaka.todo.view.ToDoView;
import com.asigaka.todo.model.ToDo;
import com.asigaka.todo.repository.ToDoRepository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ToDoController {
    private final ToDoRepository repository;
    private final String dateFormat = "dd-MM-yyyy HH:mm";
    private final int secondsToCheckDeadline = 60;

    private final ToDoView view;

    public ToDoController(ToDoRepository repository, ToDoView view) {
        this.repository = repository;
        this.view = view;
    }

    /**
     * Обрабатывает и сохраняет задачу в БД
     */
    public void SaveToDo(String description, String deadline) {
        if (getDateByString(deadline) != null) {
            Date creationDate = new Date();
            DateFormat df = new SimpleDateFormat(dateFormat);
            ToDo toDo = new ToDo(description, df.format(creationDate), deadline);
            repository.save(toDo);
        }
    }

    /**
     * Обрабатывает, сохраняет и возвращает задачу
     * @return Возвращает сохранённую таску
     */
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

    /**
     * @param dateStr Строка даты, которую нужно преобразовать в {@link Date}
     * @return Возвращает объект типа {@link Date}, который распарсили из строки
     */
    private Date getDateByString(String dateStr) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            view.ShowMsg("Incorrect date");
        }
        return date;
    }

    /**
     * Выводит все невыполненные задачи
     */
    public void ShowAllNotReadyTasks() {
        for (ToDo toDo: getTasksFromRepository()) {
            if (!toDo.getReady() && toDo.getParentId() == 0) {
                view.ShowTask(toDo.toString());
            }
        }
    }

    /**
     * @param id ID задачи, которую нужно выполнить.
     * Проверяет на существование таски с таким ID и готовность дочерних задач.
     * Если проверки пройдены, помечает задачу как выполненую {@link ToDo#getReady()}
     */
    public void CompleteTaskById(Long id) {
        ToDo toDo = getTaskById(id);
        if (toDo != null && AllChildrenTaskIsComplete(toDo.getId())) {
            toDo.setReady(true);
            view.ShowCompletedTask(toDo.toString());
            repository.save(toDo);
        }
    }

    /**
     * Проверяет, есть ли дочерние задачи у задачи с переданным ID и выполнены ли они
     */
    private boolean AllChildrenTaskIsComplete(long parentId){
        for (ToDo toDo: getTasksFromRepository()) {
            if (parentId == toDo.getParentId()) {
                if (!toDo.getReady() || !AllChildrenTaskIsComplete(toDo.getId())) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param parentId ID родительской задачи
     * @param childDescription Описание дочерней задачи
     * @param deadline Дедлайн дочерней задачи
     * Добавляет новую дочернюю задачу в БД и обновляет информацию
     */
    public void AddChildTaskToParentById(Long parentId, String childDescription, String deadline) {
        ToDo parent = getTaskById(parentId);
        if (parent != null && !parent.getReady()) {
            ToDo child = SaveAndGetToDo(childDescription, deadline);
            if (child != null) {
                child.setParentId(parent.getId());
                repository.save(parent);
                repository.save(child);
            }
        }
        // Если родительская задача выполнена, то добавить новую дочернюю задачу нельзя
        else {
            view.ShowMsg("Task completed, you cannot add a child");
        }
    }

    /**
     * @return Возвращает задачу из БД по ID
     */
    private ToDo getTaskById(Long id) {
        for (ToDo toDo: getTasksFromRepository()) {
            if (toDo.getId() == id) {
                return toDo;
            }
        }
        view.ShowMsg("There is no task with such a id");
        return null;
    }

    public void ShowAllChildrenByParentId(Long idParent) {
        for (ToDo toDo: getTasksFromRepository()) {
            if (idParent == toDo.getParentId()) {
                view.ShowTask(toDo.toString());
            }
        }
    }

    /**
     * Метод, вызывающий параллельный поток для проверки дедлайнов.
     * Проверка дедлайнов срабатывает один раз в {@link #secondsToCheckDeadline} секунд
     */
    public void RunCheckOfDeadline() {
        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(secondsToCheckDeadline);
                for (ToDo toDo: getTasksFromRepository()) {
                    if (hoursBetween(getDateByString(toDo.getCreationDate())
                            ,getDateByString(toDo.getDeadlineDate())) <= 1 && !toDo.getReady()) {
                        view.ShowDeadline(toDo.toString());
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Thread checkThread = new Thread(task);
        checkThread.start();
    }

    /**
     * @return Возвращает количество часов между датами
     */
    private int hoursBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60));
    }

    /**
     *  @return Возвращает список со всеми задачами из БД
     */
    private Iterable<ToDo> getTasksFromRepository() {
        return repository.findAll();
    }
}
