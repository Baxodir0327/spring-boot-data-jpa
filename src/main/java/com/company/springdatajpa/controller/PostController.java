package com.company.springdatajpa.controller;

import com.company.springdatajpa.dto.IPostDTO;
import com.company.springdatajpa.dto.PostDTO;
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
    public List<Post> getAllByCreatorID(@PathVariable Integer userId) {
        return postRepository.getAllPostsByUserId(userId);
    }

    @DeleteMapping("/{userId}")
    /*@ResponseStatus(HttpStatus.NO_CONTENT)*/
    public ResponseEntity<?> deletePostsByUserId(@PathVariable Integer userId) {
        postRepository.deletePostsByUserId(userId);
        /*return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);*/
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/sortbyid")
    public List<Post> getAllPostsWithSortedColumns() {
        /*Sort sort = Sort.by(Sort.Direction.DESC, "title")
                .and(Sort.by(Sort.Direction.ASC, "id"));*/
        Sort.Order title = Sort.Order.desc("title");
        Sort.Order id = Sort.Order.asc("id");
        Sort sort = Sort.by(title, id);
        return postRepository.findAll(sort);
    }


    @PostMapping
    public Post save(@RequestBody Post post) {
        return customPostRepository.save(post);
    }

    @GetMapping("/query/{title}/{userId}")
    public Post findByTitle(@PathVariable String title, @PathVariable Integer userId) {
        return postRepository.findByTitleIgnoreCaseAndUserId(title, userId);
    }

    @GetMapping("/query-st/{title}")
    public List<Post> findByTitleSt(@PathVariable String title) {
        return postRepository.findAllByTitleStartingWith(title);
    }

    @GetMapping("/query-en/{title}")
    public List<Post> findByTitleEn(@PathVariable String title) {
        return postRepository.findAllByTitleEndingWith(title);
    }

    @GetMapping("/interface-projection/{userId}")
    public List<IPostDTO> interfaceProjection(@PathVariable Integer userId) {
        return postRepository.findAllByUserIdLessThanEqual(userId);
    }

    @GetMapping("/class-projection/{userId}")
    public List<PostDTO> classProjection(@PathVariable Integer userId) {
        return postRepository.findAllByUserIdGreaterThanEqual(userId);
    }


}
