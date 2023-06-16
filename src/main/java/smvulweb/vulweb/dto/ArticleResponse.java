package smvulweb.vulweb.dto;

import lombok.Getter;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ArticleResponse {

    private Long id;
    private String title;
    private String content;
    private String author;
    private List<CommentResponse> comments;

    public ArticleResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor();
        this.comments = article.getComments().stream().map(CommentResponse::new).collect(Collectors.toList());
    }
}
