package com.asigaka.todo;

public interface ToDoView {
    void StartShowAllMenus();
    void ShowTask(String toDo);
    void ShowDeadline(String toDo);
    void ShowMsg(String msg);
    void WriteAndSaveNewTask();
    void WriteAndSaveNewChildTask();
    void CompleteTask();
    void ShowAllChildren();
}
