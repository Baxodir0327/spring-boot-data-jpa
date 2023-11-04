package com.company.springdatajpa.controller;

import com.company.springdatajpa.entity.Post;
import com.company.springdatajpa.repository.CustomPostRepository;
import com.company.springdatajpa.repository.PostRepository;
import com.company.springdatajpa.util.AppConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(PostController.BASE_PATH)
public class PostController {
    public static final String BASE_PATH = AppConstant.BASE_PATH + "/posts";
    private final PostRepository postRepository;
    private final CustomPostRepository customPostRepository;

    //    @GetMapping
//    List<Post> getAll() {
//        return postRepository.findAll();
//    }
    @GetMapping
    public Page<Post> getAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Sort sort = Sort.by(Sort.Order.desc("title"), Sort.Order.asc("userId"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.findAll(pageable);
    }

    @GetMapping("/paged")
    public Page<Post> getAllPaged(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Sort sort = Sort.by(Sort.Order.desc("title"), Sort.Order.asc("user_id"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return postRepository.getAllPostsPaged(pageable);
    }

    @GetMapping("/byusers/{userIds}")
    public List<Post> getAllPaged(@PathVariable Collection<Integer> userIds) {
        return postRepository.getAllPostsByUserIds(userIds);
    }

    @GetMapping("/{userId}")
    List<Post> getAllByCreatorId(@PathVariable Integer userId) {
        return postRepository.getAllPostsByUserId1(userId);
    }

    @GetMapping("/sort")
    List<Post> getAllBySortColumn() {
        Sort sort = Sort.by(Sort.Direction.ASC, "title").and(Sort.by(Sort.Direction.DESC, "id"));
        return postRepository.findAll(sort);
    }
    @DeleteMapping("/{userId}")
    /*@ResponseStatus(HttpStatus.NO_CONTENT)*/
    public ResponseEntity<?> deletePostsByUserId(@PathVariable Integer userId) {
        postRepository.deletePostsByUserId(userId);
        /*return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);*/
        return ResponseEntity.noContent().build();
    }
    @PostMapping
    public Post save(@RequestBody Post post) {
        return customPostRepository.save(post);
    }


}
