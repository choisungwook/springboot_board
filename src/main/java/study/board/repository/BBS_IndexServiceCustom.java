package study.board.repository;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import study.board.domain.dto.index.Response_index_posts_dto;

public interface BBS_IndexServiceCustom {
    PageImpl<Response_index_posts_dto> findPostsfromId(Long id, Pageable pageable);
}
