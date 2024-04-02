package com.hoang.springapijwt.service.todo;


import com.hoang.springapijwt.models.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    void addTodo(Todo todo);
    List<Todo> getAllToto(int userId);

    Todo update(Todo todo);

    void delete(int id);

    Optional<Todo> get(int id);
}
