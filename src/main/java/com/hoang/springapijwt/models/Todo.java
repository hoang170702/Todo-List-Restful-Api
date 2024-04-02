package com.hoang.springapijwt.models;


import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name ="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String imgUrl;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;


}
