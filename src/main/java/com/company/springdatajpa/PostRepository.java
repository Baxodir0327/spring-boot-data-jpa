package com.company.springdatajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path = "ketmonlar",collectionResourceRel = "bolta")
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findByTitleIgnoreCase(String title);
}