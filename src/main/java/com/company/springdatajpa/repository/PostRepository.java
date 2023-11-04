package com.company.springdatajpa.repository;

import com.company.springdatajpa.dto.IPostDTO;
import com.company.springdatajpa.dto.PostDTO;
import com.company.springdatajpa.entity.Post;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    // jpql
    // @Query(value = "select p from Post p where p.userId = ?1")
    // native query
    // @Query(nativeQuery = true, value = "select p.* from post p where p.user_id = ?1")
    // namedquery
    // @Query(name = "Post.getAllPostsByUserID")
    @Query(nativeQuery = true, name = "Post.getAllPostsByUserID.Native")
    List<Post> getAllPostsByUserId(Integer userID);

    @Query(value = "select p from Post p")
    List<Post> getAllPostsWithSortedColumns(Sort sort);

    // @Query(value = "select p from Post p")
    @Query(nativeQuery = true,
            value = "select p.* from post p",
            countQuery = "select count(1) from post p"
    )
    Page<Post> getAllPostsPaged(Pageable pageable);

    @Query("from Post p where p.userId in (?1)")
    List<Post> getAllPostsByUserIds(Collection<Integer> userIds);


    @Modifying
    @Transactional
    @Query("delete Post p where p.userId = ?1")
    void deletePostsByUserId(Integer userId);

    Post findByTitleIgnoreCaseAndUserId(String title, Integer userId);

    List<Post> findAllByTitleStartingWith(String title);

    List<Post> findAllByTitleEndingWith(String title);

    List<IPostDTO> findAllByUserIdLessThanEqual(Integer userId);

    List<PostDTO> findAllByUserIdGreaterThanEqual(Integer userId);

    //JPQL
    //@Query("select new com.company.springdatajpa.dto.PostDTO(p.id,p.title,p.userId) FROM Post p")
    // Native Query
     @Query(nativeQuery = true, name = "Post.find.all.by.projection")
    List<PostDTO> findAllByClassProjection();

}
