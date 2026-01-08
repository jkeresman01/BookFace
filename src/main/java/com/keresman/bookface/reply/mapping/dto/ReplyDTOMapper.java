
package com.keresman.bookface.reply.mapping.dto;

import com.keresman.bookface.reply.dto.response.ReplyDTO;
import com.keresman.bookface.reply.entity.Reply;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ReplyDTOMapper implements Function<Reply, ReplyDTO> {

    @Override
    public ReplyDTO apply(Reply reply) {
        return new ReplyDTO(
                reply.getId(),
                reply.getContent(),
                reply.getUser().getId(),
                reply.getUser().getUsername(),
                reply.getComment().getId(),
                reply.getComment().getContent()
        );
    }
}