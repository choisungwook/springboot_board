package study.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.board.domain.dto.index.Response_index_posts_dto;

public interface BBS_IndexServiceCustom {
    Page<Response_index_posts_dto> findPostsfromId(Long id, Pageable pageable);
}
