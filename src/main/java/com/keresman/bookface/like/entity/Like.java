package com.keresman.bookface.like.entity;

import com.keresman.bookface.post.entity.Post;
import com.keresman.bookface.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Like")
@Table(
        name = "likes",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "user_post_like_unique",
                        columnNames = {"user_id", "post_id"}
                )
        }
)
public final class Like {

    @Id
    @SequenceGenerator(
            name = "like_sequence",
            sequenceName = "like_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "like_sequence"
    )
    private Long id;

    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "like_user_id_fkey"
            )
    )
    private User user;

    @ManyToOne()
    @JoinColumn(
            name = "post_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "like_post_id_fkey"
            )
    )
    private Post post;
}
