package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smvulweb.vulweb.ErrorResponse;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.dto.AddArticleRequest;
import smvulweb.vulweb.dto.ArticleResponse;
import smvulweb.vulweb.dto.UpdateArticleRequest;
import smvulweb.vulweb.service.BoardService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping("/test")
    public Map<String, Object> testHandler() {

        Map<String, Object> res = new HashMap<>();
        res.put("SUCCESS", true);
        res.put("SUCCESS_TEXT", "Hello SpringBoot/React");

        return res;
    }
    @PostMapping("/api/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = boardService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
        List<ArticleResponse> articles = boardService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(articles);
    }

    @GetMapping("/api/article/{id}")
    public ResponseEntity<?> findArticle(@PathVariable long id) {
        try {
            Article article = boardService.findById(id);
            return ResponseEntity.ok().body(new ArticleResponse(article));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("페이지가 없음"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("서버 오류"));
        }
    }

    @GetMapping("/api/article/delete/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        boardService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @GetMapping("/api/article/update/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = boardService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }

}
