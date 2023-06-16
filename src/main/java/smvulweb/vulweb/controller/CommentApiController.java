package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import smvulweb.vulweb.domain.Comment;
import smvulweb.vulweb.dto.AddCommentRequest;
import smvulweb.vulweb.service.CommentService;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/articles/{id}/comments")
    public ResponseEntity<String> addComment(@PathVariable Long id,
                                              @RequestBody AddCommentRequest request) {
        Comment savedComment = commentService.save(id, request);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
