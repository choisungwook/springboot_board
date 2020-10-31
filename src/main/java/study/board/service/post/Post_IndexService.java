package study.board.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Post;
import study.board.domain.dto.index.Response_index_posts_dto;
import study.board.repository.PostRepository;

import java.util.List;
import java.util.stream.Collectors;

/***
 * 메인화면용 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Post_IndexService {
    private final PostRepository postRepository;

    public List<Response_index_posts_dto> findAll(){
        List<Post> all = postRepository.findAll();

        List<Response_index_posts_dto> response = all.stream()
                .map(post -> new Response_index_posts_dto(post))
                .collect(Collectors.toList());

        return response;
    }
}
