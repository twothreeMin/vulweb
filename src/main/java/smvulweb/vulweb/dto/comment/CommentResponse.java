package smvulweb.vulweb.dto.comment;

import lombok.Getter;
import smvulweb.vulweb.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class CommentResponse {

    private final String comment;
    private final String nickname;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;


    public CommentResponse(Comment comment) {
        this.comment = comment.getComment();
        this.nickname = comment.getNickname();
        this.createdDate = comment.getCreatedDate();
        this.modifiedDate = comment.getModifiedDate();
    }
}
