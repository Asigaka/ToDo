package com.asigaka.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Главный класс, из которого запускается консольное меню
 */
@SpringBootApplication
public class TodoApplication{
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TodoApplication.class, args);
		Menu menu = new Menu(context);
		menu.MenusStart();
	}
}
