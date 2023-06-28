package smvulweb.vulweb.dto.comment;

import lombok.*;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.domain.Comment;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {

    private String nickname;
    private String comment;

    public Comment toEntity(Article article) {
            return Comment.builder()
                    .nickname(nickname)
                    .comment(comment)
                    .article(article)
                    .build();
    }
}
