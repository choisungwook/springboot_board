package study.board.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Post;
import study.board.repository.PostRepository;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class Post_GeneralService {
    private final PostRepository postRepository;

    @Transactional
    public Long save(Post post){
        return postRepository.save(post).getId();
    }

    public Post findById(Long id){
        Post find_post = postRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 게시글")
                );

        return find_post;
    }

    /***
     * 게시판 클릭
     * @param id
     * @return
     */
    public Post click(Long id){
        Post find_post = postRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 게시글")
                );

        // 조회수 증가
        find_post.increase_hit();

        return find_post;
    }

    public List<Post> findAll(){
        return postRepository.findAll();
    }

    @Transactional
    public void change_title(Long id, String title){
        Post find_post = this.findById(id);

        find_post.change_title(title);
    }

    @Transactional
    public void change_content(Long id, String content){
        Post find_post = this.findById(id);

        find_post.change_content(content);
    }

    public Post findByTitle(String title){
        Post find_post = postRepository.findByTitle(title)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 게시글")
                );

        return find_post;
    }

    @Transactional
    public void delete(Long id){
        Post find_post = this.findById(id);

        postRepository.delete(find_post);
    }
}
