package com.hoang.springapijwt.service.todo;

import com.hoang.springapijwt.models.Todo;
import com.hoang.springapijwt.repository.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService {
    @Autowired
    private TodoRepository todoRepository;

    @Override
    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    @Override
    public List<Todo> getAllToto() {
        return todoRepository.findAll();
    }

    @Override
    public Todo update(int id, Todo todo) {
        try {
            Todo exitTodo = todoRepository.findById(id).get();
            if (exitTodo != null) {
                exitTodo.setTitle(todo.getTitle());
                exitTodo.setContent(todo.getContent());
                if (!todo.getImgUrl().isEmpty()) {
                    exitTodo.setImgUrl(todo.getImgUrl());
                }
            }
            return exitTodo;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return null;
        }
    }

    @Override
    public void delete(int id) {
        try {
            todoRepository.deleteById(id);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
