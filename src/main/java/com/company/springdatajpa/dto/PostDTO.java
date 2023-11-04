package com.company.springdatajpa.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDTO {
    Integer id;
    String title;
    Integer userId;


}
