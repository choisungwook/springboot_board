package study.board.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.domain.Post;
import study.board.domain.dto.index.Response_index_postview_dto;
import study.board.repository.PostRepository;

@Service
@RequiredArgsConstructor
public class Post_indexService {
    private final PostRepository postRepository;

    /***
     * 게시판 클릭
     * @param id
     * @return
     */
    public Response_index_postview_dto click(Long id){
        Post find_post = postRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 게시글")
                );
        find_post.increase_hit();

        return new Response_index_postview_dto(find_post);
    }
}
