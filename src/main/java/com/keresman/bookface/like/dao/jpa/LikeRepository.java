package com.keresman.bookface.like.dao.jpa;

import com.keresman.bookface.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("""
            SELECT l FROM Like l
            WHERE l.user.id = :userId
            """)
    List<Like> findByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT l FROM Like l
            WHERE l.post.id = :postId
            """)
    List<Like> findByPostId(@Param("postId") Long postId);

    @Query("""
            SELECT l FROM Like l
            WHERE l.user.id = :userId
            AND l.post.id = :postId
            """)
    Optional<Like> findByUserIdAndPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId
    );

    @Query("""
            SELECT COUNT(l) > 0 FROM Like l
            WHERE l.user.id = :userId
            AND l.post.id = :postId
            """)
    boolean existsByUserIdAndPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId
    );

    @Modifying
    @Query("""
            DELETE FROM Like l
            WHERE l.user.id = :userId
            AND l.post.id = :postId
            """)
    void deleteByUserIdAndPostId(
            @Param("userId") Long userId,
            @Param("postId") Long postId
    );

    @Query("""
            SELECT COUNT(l) FROM Like l
            WHERE l.post.id = :postId
            """)
    int countByPostId(@Param("postId") Long postId);
}