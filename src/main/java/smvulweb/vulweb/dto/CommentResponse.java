package smvulweb.vulweb.dto;

import lombok.Getter;
import smvulweb.vulweb.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponse {

    private final String comment;
    private final String nickname;


    public CommentResponse(Comment comment) {
        this.comment = comment.getComment();
        this.nickname = comment.getNickname();
    }
}
