package study.board.repository;

import study.board.domain.Post;

import java.util.List;

public interface BBS_IndexServiceCustom {
    List<Post> findPostsfromId(Long id);
}
