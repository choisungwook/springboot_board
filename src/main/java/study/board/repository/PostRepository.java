package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.domain.Post;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByTitle(String title);
}
