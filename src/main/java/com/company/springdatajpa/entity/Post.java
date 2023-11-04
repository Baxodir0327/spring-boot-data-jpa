package com.company.springdatajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@NamedQuery(name = "getAllByUserId", query = "SELECT p FROM Post p WHERE p.userId=:userId")
@NamedNativeQuery(name = "getAllByUserI.native",query = "SELECT p.* FROM post p WHERE p.user_id=?1" ,
        resultClass = Post.class
)
public class Post {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
}
