package com.asigaka.todo;

import com.asigaka.todo.model.ToDo;
import com.asigaka.todo.repository.ToDoRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
public class TodoApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TodoApplication.class, args);
		ToDoRepository repository = context.getBean(ToDoRepository.class);
		ToDoController toDoController = new ToDoController(repository);

		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("-----------------------");
			System.out.println("Commands list:");
			System.out.println("1. Create new task");
			System.out.println("2. Show all tasks");
			System.out.println("-----------------------");
			System.out.println("Enter command number:");
			int commandNum = scanner.nextInt();
			scanner.nextLine();
			System.out.println("-----------------------");
			switch (commandNum) {
				case 1:
					System.out.println("Enter description:");
					String description = scanner.nextLine();

					System.out.println("Enter deadline of task:" +
							"\nExample: 10-01-2022");
					String deadline = scanner.nextLine();
					toDoController.SaveToDo(description, deadline);
					break;
				case 2:
					Iterable<ToDo> todoes = repository.findAll();
					for (ToDo dos: todoes) {
						System.out.println(dos.toString());
					}
					break;
				default:
					System.out.println("Number of command incorrect");
					break;
			}
		}
	}
}
