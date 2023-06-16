package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.domain.Comment;
import smvulweb.vulweb.dto.AddCommentRequest;
import smvulweb.vulweb.repository.BoardRepository;
import smvulweb.vulweb.repository.CommentRepository;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment save(Long articleId, AddCommentRequest request) {
        Article article = boardRepository.findById(articleId)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + articleId));

        return commentRepository.save(request.toEntity(article));
    }
}
