package smvulweb.vulweb.dto;

import lombok.*;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.domain.Comment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {

    private Long id;
    private String nickname;
    private String comment;

    private String createdDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
    private String modifiedDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));

    private Article article;

    public Comment toEntity(Article article) {
            return Comment.builder()
                    .nickname(nickname)
                    .comment(comment)
                    .createdDate(createdDate)
                    .modifiedDate(modifiedDate)
                    .article(article)
                    .build();
    }
}
