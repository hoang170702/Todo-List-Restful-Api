package com.hoang.springapijwt.service.todo;


import com.hoang.springapijwt.models.Todo;

import java.util.List;

public interface TodoService {
    void addTodo(Todo todo);
    List<Todo> getAllToto();

    Todo update(int id, Todo todo);

    void delete(int id);
}
