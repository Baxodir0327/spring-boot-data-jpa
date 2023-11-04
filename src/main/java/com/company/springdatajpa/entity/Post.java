package com.company.springdatajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
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
