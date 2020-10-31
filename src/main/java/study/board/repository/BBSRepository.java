package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.domain.BBS;

public interface BBSRepository extends JpaRepository<BBS, Long> {
    boolean existsByTitle(String Title);
}
