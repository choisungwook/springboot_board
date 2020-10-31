package study.board.service.post;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.BBS;
import study.board.domain.Post;
import study.board.repository.BBSRepository;
import study.board.repository.PostRepository;
import study.board.service.bbs.BBS_GeneralService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class Post_GeneralServiceTest {
    @Autowired Post_GeneralService post_generalService;
    @Autowired PostRepository postRepository;
    @Autowired BBS_GeneralService bbs_generalService;
    @Autowired BBSRepository bbsRepository;

    @AfterEach
    public void clear(){
        postRepository.deleteAll();
        bbsRepository.deleteAll();
    }

    @Test
    public void 게시판1개_게시글1개_생성(){
        //given, when
        BBS board1 = create_board("게시판1");
        Post post1 = create_post("테스트글1", "안녕하세요", board1);

        //then
        System.out.println("게시판: " + board1.getTitle());
        board1.getPosts().stream()
                .forEach(p -> System.out.println("게시글 제목: " + p.getTitle() + ", " + "게시글 내용: " + p.getContent()));
    }

    @Test
    public void 게시판1개_게시글n개_생성(){
        int post_length = 10;
        List<Post> posts = new ArrayList<>();
        //given, when
        BBS board1 = create_board("게시판1");
        for (int i = 0; i < post_length; i++) {
            String title = "게시판" + Integer.toString(i);
            Post post = create_post(title, "hello world", board1);
            posts.add(post);
        }

        //then
        int posts_size = post_generalService.findAll().size();
        assertThat(posts.size()).isEqualTo(posts_size);
        System.out.println("게시판: " + board1.getTitle());
        board1.getPosts().stream()
                .forEach(p -> System.out.println("게시글 제목: " + p.getTitle() + ", " + "게시글 내용: " + p.getContent()));
    }

    @Test
    public void 게시판n개_게시글n개_생성(){
        int post_length = 10;
        List<BBS> boards = new ArrayList<>();

        // board1
        List<Post> board1_posts = new ArrayList<>();
        BBS board1 = create_board("게시판1");
        for (int i = 0; i < post_length; i++) {
            String title = "게시판" + Integer.toString(i);
            Post post = create_post(title, "hello world", board1);
            board1_posts.add(post);
        }
        boards.add(board1);

        //board2
        List<Post> board2_posts = new ArrayList<>();
        BBS board2 = create_board("게시판2");
        for (int i = 0; i < post_length; i++) {
            String title = "게시판" + Integer.toString(i);
            Post post = create_post(title, "hello world", board2);
            board2_posts.add(post);
        }
        boards.add(board2);

        // then
        int posts_size = post_generalService.findAll().size();
        assertThat(post_length * 2).isEqualTo(posts_size);

        for (BBS board : boards) {
            System.out.println("board: " + board.getTitle());

            board.getPosts().stream()
                    .forEach(p -> System.out.println("게시글 제목: " + p.getTitle() + ", " + "게시글 내용: " + p.getContent()));
            System.out.println("============================");
        }
    }


    private Post create_post(String title, String content, BBS bbs){
        // given
        Post new_post = Post.builder()
                .title(title)
                .content(content)
                .bbs(bbs)
                .build();

        // when
        Long saveId = post_generalService.save(new_post);

        // then
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDateTime now = LocalDateTime.now();
        Post find_post = post_generalService.findById(saveId);

        assertThat(find_post.getTitle()).isEqualTo(new_post.getTitle());
        assertThat(find_post.getContent()).isEqualTo(new_post.getContent());
        assertThat(find_post.getHit()).isEqualTo(new_post.getHit());
        assertThat(find_post.getCreateDate()).isBefore(now);
        assertThat(find_post.getLastModifiedDate()).isBefore(now);

        return find_post;
    }

    private BBS create_board(String title){
        //given
        BBS new_bbs = BBS.builder()
                .title(title)
                .build();

        //when
        Long saveId = bbs_generalService.save(new_bbs);
        // 게시판 중복
        if(saveId == -1) throw new IllegalStateException("이미 존재하는 게시판");

        //then
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalDateTime now = LocalDateTime.now();
        BBS find_bbs = bbs_generalService.findById(saveId);
        assertThat(find_bbs.getId()).isEqualTo(new_bbs.getId());
        assertThat(find_bbs.getTitle()).isEqualTo(new_bbs.getTitle());
        assertThat(find_bbs.getCreateDate()).isBefore(now);
        assertThat(find_bbs.getLastModifiedDate()).isBefore(now);

        return find_bbs;
    }

}