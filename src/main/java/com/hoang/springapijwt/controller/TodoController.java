package com.hoang.springapijwt.controller;


import com.hoang.springapijwt.models.Todo;
import com.hoang.springapijwt.service.todo.TodoService;
import com.hoang.springapijwt.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/todo")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @Autowired
    private UserService userService;

    @GetMapping("/todos")
    public ResponseEntity<?> showTodo() {
        try {
            int userId = userService.getCurrentUser().getId();
            List<Todo> todos = todoService.getAllToto(userId);
            if (!todos.isEmpty()) {
                log.info("Get todo from user: " + userId);
            }
            return ResponseEntity.status(HttpStatus.OK).body(todos);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PostMapping("/add-todo")
    public ResponseEntity<?> addTodo(@RequestBody Todo todo){
        try {
            var user = userService.getCurrentUser();
            todo.setUser(user);
            todoService.addTodo(todo);
            log.info("Add todo success");
            return ResponseEntity.status(HttpStatus.OK).body("Add Todo success");
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @PutMapping("/update-todo")
    public ResponseEntity<?> updateTodo(@RequestParam int id,@RequestBody Todo todo){
        try {
                var user = userService.getCurrentUser();
                var exitTodo = todoService.get(id);
                if(exitTodo.isPresent()){
                    if(user.getId() == exitTodo.get().getUser().getId()){
                        exitTodo.get().setTitle(todo.getTitle());
                        exitTodo.get().setImgUrl(todo.getImgUrl());
                        todoService.addTodo(exitTodo.get());
                        return ResponseEntity.status(HttpStatus.OK).body("Update Todo success");
                    }
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you dont have permissions");
                }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo is not valid");
        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable("id") int id){
        try {
            var user = userService.getCurrentUser();
            var exitTodo = todoService.get(id);
            if(exitTodo.isPresent()){
                if(user.getId() == exitTodo.get().getUser().getId()){
                    exitTodo.ifPresent(todo -> todoService.delete(todo.getId()));
                    return ResponseEntity.status(HttpStatus.OK).body("deleted todo: "+ id);
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("you dont have permissions");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Todo is not valid");

        }catch (Exception ex){
            log.error(ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
    }
}
