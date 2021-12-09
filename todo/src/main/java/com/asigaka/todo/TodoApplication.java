package com.asigaka.todo;

import com.asigaka.todo.repository.ToDoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.*;

@SpringBootApplication
public class TodoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TodoApplication.class, args);
		ToDoRepository repository = context.getBean(ToDoRepository.class);
		ToDoController toDoController = new ToDoController(repository);

		toDoController.RunCheckOfDeadline();

		Scanner scanner = new Scanner(System.in);
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
					System.out.println("Enter description:");
					String description = scanner.nextLine();

					System.out.println("Enter deadline of task:" +
							"\nExample: 10-01-2022 12:23");
					String deadline = scanner.nextLine();
					toDoController.SaveToDo(description, deadline);
					break;
				case 2:
					toDoController.ShowAllReadyTasks();
					break;
				case 3:
					toDoController.ShowAllNotReadyTasks();
					break;
				case 4:
					System.out.println("Enter task id");
					toDoController.CompleteTaskById(scanner.nextLong());
					break;
				case 5:
					System.out.println("Enter parent task id");
					Long parentId = scanner.nextLong();
					scanner.nextLine();

					System.out.println("Enter description:");
					String childDescription = scanner.nextLine();

					System.out.println("Enter deadline of task:" +
							"\nExample: 10-01-2022 12:23");
					String childDeadline = scanner.nextLine();

					toDoController.AddChildTaskToParentById(parentId, childDescription, childDeadline);
					break;
				case 6:
					System.out.println("Enter parent task id");
					toDoController.ShowAllChildrenByParentId(scanner.nextLong());
					scanner.nextLine();
					break;
				default:
					System.out.println("Number of command incorrect");
					break;
			}
		}
	}
}
