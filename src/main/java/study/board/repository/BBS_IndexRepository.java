package study.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.board.domain.BBS;

public interface BBS_IndexRepository extends JpaRepository<BBS, Long>, BBS_IndexServiceCustom {
}
