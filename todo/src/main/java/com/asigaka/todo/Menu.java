package com.asigaka.todo;

import com.asigaka.todo.controller.ToDoController;
import com.asigaka.todo.repository.ToDoRepository;
import com.asigaka.todo.view.ToDoView;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

//Попытался сделать нормальный View для вывода текста в консоль:)
public class Menu implements ToDoView {
    private ConfigurableApplicationContext context;
    private ToDoController toDoController;
    private Scanner scanner;

    public Menu(ConfigurableApplicationContext context) {
        this.context = context;
    }

    /**
     * Основной метод, начинающий полноценную работу меню
     */
    public void MenusStart(){
        ToDoRepository repository = context.getBean(ToDoRepository.class);
        toDoController = new ToDoController(repository, this);
        toDoController.RunCheckOfDeadline();
        scanner = new Scanner(System.in);

        StartShowAllMenus();
    }

    @Override
    public void StartShowAllMenus() {
        while (true) {
            System.out.println("-----------------------");
            System.out.println("Commands list:");
            System.out.println("1. Create new task");
            System.out.println("2. Show all not ready tasks");
            System.out.println("3. Complete task");
            System.out.println("4. Add child task");
            System.out.println("5. Show all children by Id");
            System.out.println("-----------------------");
            System.out.println("Enter number of command:");
            int commandNum = scanner.nextInt();
            scanner.nextLine();
            System.out.println("-----------------------");
            switch (commandNum) {
                case 1:
                    WriteAndSaveNewTask();
                    break;
                case 2:
                    ShowAllNotReadyTasks();
                    break;
                case 3:
                    CompleteTask();
                    break;
                case 4:
                    WriteAndSaveNewChildTask();
                    break;
                case 5:
                    ShowAllChildren();
                    break;
                default:
                    ShowMsg("Number of command incorrect");
                    break;
            }
        }
    }

    @Override
    public void ShowAllNotReadyTasks() {
        toDoController.ShowAllNotReadyTasks();
    }

    @Override
    public void ShowTask(String toDo) {
        System.out.println("==========");
        System.out.println(toDo);
        System.out.println("==========");
    }

    @Override
    public void ShowDeadline(String toDo) {
        System.out.println("==========");
        System.out.println("!Deadline will end soon!");
        System.out.println(toDo);
        System.out.println("==========");
    }

    @Override
    public void ShowMsg(String msg) {
        System.out.println(msg);
    }

    @Override
    public void ShowCompletedTask(String toDo) {
        System.out.println(toDo + "\nis complete!");
    }

    @Override
    public void WriteAndSaveNewTask() {
        toDoController.SaveToDo( GetRecordedDescription(), GetRecordedDeadline());
    }

    @Override
    public void WriteAndSaveNewChildTask() {
        System.out.println("Enter parent task id");
        Long parentId = scanner.nextLong();
        scanner.nextLine();

        toDoController.AddChildTaskToParentById(parentId, GetRecordedDescription(), GetRecordedDeadline());
    }

    @Override
    public void CompleteTask() {
        System.out.println("Enter task id");
        toDoController.CompleteTaskById(scanner.nextLong());
    }

    @Override
    public void ShowAllChildren() {
        System.out.println("Enter parent task id");
        toDoController.ShowAllChildrenByParentId(scanner.nextLong());
        scanner.nextLine();
    }

    /**
     * @return Возвращает записанное пользователем описание
     */
    @Override
    public String GetRecordedDescription() {
        System.out.println("Enter description:");
        return scanner.nextLine();
    }

    /**
     * @return Возвращает записанный пользователем дедлайн
     */
    @Override
    public String GetRecordedDeadline() {
        System.out.println("Enter deadline of task:" +
                "\nExample: 10-01-2022 12:23");
        return scanner.nextLine();
    }
}
