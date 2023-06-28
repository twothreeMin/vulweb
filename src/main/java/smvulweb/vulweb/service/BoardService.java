package smvulweb.vulweb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smvulweb.vulweb.domain.Article;
import smvulweb.vulweb.dto.article.AddArticleRequest;
import smvulweb.vulweb.dto.article.UpdateArticleRequest;
import smvulweb.vulweb.repository.BoardRepository;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Article save(AddArticleRequest request) {
        return boardRepository.save(request.toEntity());
    }

    public List<Article> findAll() {
        return boardRepository.findAll();
    }

    public Article findById(long id) {
        return boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));
    }

    public void delete(long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = boardRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: " + id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }
}
