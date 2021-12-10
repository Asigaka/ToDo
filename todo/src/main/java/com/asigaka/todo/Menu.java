package com.asigaka.todo;

import com.asigaka.todo.controller.ToDoController;
import com.asigaka.todo.repository.ToDoRepository;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

public class Menu implements ToDoView{
    private ConfigurableApplicationContext context;
    private ToDoController toDoController;
    private Scanner scanner;

    public Menu(ConfigurableApplicationContext context) {
        this.context = context;
    }

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
            System.out.println("2. Show all ready tasks");
            System.out.println("3. Show all not ready tasks");
            System.out.println("4. Complete task");
            System.out.println("5. Add child task");
            System.out.println("6. Show all children by Id");
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
                    toDoController.ShowAllReadyTasks();
                    break;
                case 3:
                    toDoController.ShowAllNotReadyTasks();
                    break;
                case 4:
                   CompleteTask();
                    break;
                case 5:
                    WriteAndSaveNewChildTask();
                    break;
                case 6:
                    ShowAllChildren();
                    break;
                default:
                    ShowMsg("Number of command incorrect");
                    break;
            }
        }
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
    public void WriteAndSaveNewTask() {
        System.out.println("Enter description:");
        String description = scanner.nextLine();

        System.out.println("Enter deadline of task:" +
                "\nExample: 10-01-2022 12:23");
        String deadline = scanner.nextLine();
        toDoController.SaveToDo(description, deadline);
    }

    @Override
    public void WriteAndSaveNewChildTask() {
        System.out.println("Enter parent task id");
        Long parentId = scanner.nextLong();
        scanner.nextLine();

        System.out.println("Enter description:");
        String childDescription = scanner.nextLine();

        System.out.println("Enter deadline of task:" +
                "\nExample: 10-01-2022 12:23");
        String childDeadline = scanner.nextLine();

        toDoController.AddChildTaskToParentById(parentId, childDescription, childDeadline);
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
}
