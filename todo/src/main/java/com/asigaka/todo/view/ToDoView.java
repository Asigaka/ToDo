package com.asigaka.todo.view;

public interface ToDoView {
    void StartShowAllMenus();
    void ShowAllNotReadyTasks();
    void ShowTask(String toDo);
    void ShowDeadline(String toDo);
    void ShowMsg(String msg);
    void ShowCompletedTask(String toDo);
    void WriteAndSaveNewTask();
    void WriteAndSaveNewChildTask();
    void CompleteTask();
    void ShowAllChildren();
    String GetRecordedDescription();
    String GetRecordedDeadline();
}
