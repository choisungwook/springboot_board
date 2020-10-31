package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.domain.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
