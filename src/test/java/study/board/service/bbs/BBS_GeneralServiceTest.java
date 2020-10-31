package study.board.service.bbs;

import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.BBS;
import study.board.domain.Post;
import study.board.domain.Reply;
import study.board.repository.BBSRepository;
import study.board.repository.PostRepository;
import study.board.repository.ReplyRepository;
import study.board.service.post.Post_GeneralService;
import study.board.service.reply.Reply_GeneralService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class BBS_GeneralServiceTest {
    @Autowired BBS_GeneralService bbs_generalService;
    @Autowired BBSRepository bbsRepository;
    @Autowired Post_GeneralService post_generalService;
    @Autowired PostRepository postRepository;
    @Autowired Reply_GeneralService reply_generalService;
    @Autowired ReplyRepository replyRepository;

    @AfterEach
    public void clear(){
        bbsRepository.deleteAll();
        postRepository.deleteAll();
        replyRepository.deleteAll();
    }

    @Test
    public void 게시판생성(){
        BBS board = create_board("테스트 제목");
    }

    @Test(expected=IllegalStateException.class)
    public void 게시판중복_생성검사(){
        //given, when
        BBS board1 = create_board("테스트 제목");
        BBS board2 = create_board("테스트 제목");

        //then
        fail("게시판 중복검사 실패");
    }

    @Test
    public void n개_게시글이_있는_게시판삭제(){
        int post_length = 10;
        List<Post> posts = new ArrayList<>();
        //given, when
        BBS board1 = create_board("게시판1");
        for (int i = 0; i < post_length; i++) {
            String title = "게시판" + Integer.toString(i);
            Post post = create_post(title, "hello world", board1);
            posts.add(post);
        }

        // when
        bbs_generalService.delete(board1.getId());

        //then
        assertThat(bbs_generalService.findAll().size()).isEqualTo(0L);
        assertThat(post_generalService.findAll().size()).isEqualTo(0L);
    }

    @Test
    public void 댓글이_있는_게시글을_포함한게시판_삭제(){
        int reply_length = 10;
        List<Reply> replies = new ArrayList<>();
        //given
        BBS board1 = create_board("게시판1");
        Post post1 = create_post("테스트글1", "안녕하세요", board1);
        for (int i = 0; i < reply_length; i++) {
            String content = "댓글" + Integer.toString(i);
            replies.add(create_reply(content, post1));
        }

        // when
        bbs_generalService.delete(board1.getId());

        //then
        assertThat(bbs_generalService.findAll().size()).isEqualTo(0L);
        assertThat(post_generalService.findAll().size()).isEqualTo(0L);
        assertThat(reply_generalService.findAll().size()).isEqualTo(0L);
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

    private Reply create_reply(String content, Post post){
        // given
        Reply new_reply = Reply.builder()
                .content(content)
                .post(post)
                .build();

        // when
        Long saveId = reply_generalService.save(new_reply);

        // then
        Reply find_reply = reply_generalService.findById(saveId);
        assertThat(find_reply.getId()).isEqualTo(new_reply.getId());
        assertThat(find_reply.getContent()).isEqualTo(new_reply.getContent());

        return find_reply;
    }

}