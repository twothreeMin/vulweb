package smvulweb.vulweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smvulweb.vulweb.domain.Article;

public interface BoardRepository extends JpaRepository<Article, Long> {
}
