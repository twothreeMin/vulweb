package smvulweb.vulweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smvulweb.vulweb.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
