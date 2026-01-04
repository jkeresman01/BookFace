package com.keresman.bookface.post.dao.jpa;

import com.keresman.bookface.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(
            """
            SELECT p FROM Post p
            WHERE p.user.id = :userId
            """
    )
    List<Post> findByUserId(@Param("userId") Long userId);
}
