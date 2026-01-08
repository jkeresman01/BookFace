
package com.keresman.bookface.reply.entity;

import com.keresman.bookface.comment.entity.Comment;
import com.keresman.bookface.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Reply")
@Table(name = "reply")
public final class Reply {

    @Id
    @SequenceGenerator(
            name = "reply_sequence",
            sequenceName = "reply_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reply_sequence"
    )
    private Long id;

    @Column(
            name = "content",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String content;

    @ManyToOne()
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "reply_user_id_fkey"
            )
    )
    private User user;

    @ManyToOne()
    @JoinColumn(
            name = "comment_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "reply_comment_id_fkey"
            )
    )
    private Comment comment;
}