package smvulweb.vulweb.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smvulweb.vulweb.domain.Comment;
import smvulweb.vulweb.dto.comment.AddCommentRequest;
import smvulweb.vulweb.dto.comment.CommentResponse;
import smvulweb.vulweb.dto.comment.UpdateCommentRequest;
import smvulweb.vulweb.service.CommentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/article/{id}/comments")
    public ResponseEntity<String> addComment(@PathVariable Long id,
                                              @RequestBody AddCommentRequest request) {
        Comment savedComment = commentService.save(id, request);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/delete/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable long id) {
        commentService.delete(id);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @GetMapping("/update/comment/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable long id,
                                                 @RequestBody UpdateCommentRequest request) {
        Comment updateComment = commentService.update(id, request);
        CommentResponse commentResponse = new CommentResponse(updateComment);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
