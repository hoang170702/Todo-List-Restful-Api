package com.hoang.springapijwt.repository;

import com.hoang.springapijwt.models.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    @Query("select todo from Todo todo join todo.user user where user.id = :userId")
    List<Todo> findAllByUserId(@Param("userId") int userId);
}
