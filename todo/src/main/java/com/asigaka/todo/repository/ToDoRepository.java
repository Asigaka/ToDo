package com.asigaka.todo.repository;

import com.asigaka.todo.model.ToDo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Long> {

}
