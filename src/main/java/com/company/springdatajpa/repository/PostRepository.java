package com.company.springdatajpa.repository;

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
    //    @Query(value = "SELECT p FROM Post p WHERE p.userId=:userId")//jpql
    //    @Query(nativeQuery = true, value = "SELECT p.* FROM post p WHERE p.user_id=:userId")
    //namedQuery
    //@Query(name = "getAllByUserId")
    @Query(nativeQuery = true, name = "getAllByUserI.native")
    List<Post> getAllPostsByUserId1(Integer userId);

    @Query("SELECT p FROM Post p")
    List<Post> getAllPostWithSortedColumn(Sort sort);

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
}
