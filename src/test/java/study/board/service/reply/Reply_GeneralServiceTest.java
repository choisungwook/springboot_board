package study.board.service.reply;


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
import study.board.service.bbs.BBS_GeneralService;
import study.board.service.post.Post_GeneralService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class Reply_GeneralServiceTest {
    @Autowired Post_GeneralService post_generalService;
    @Autowired PostRepository postRepository;
    @Autowired BBS_GeneralService bbs_generalService;
    @Autowired BBSRepository bbsRepository;
    @Autowired Reply_GeneralService reply_generalService;
    @Autowired ReplyRepository replyRepository;

    @AfterEach
    public void clear(){
        postRepository.deleteAll();
        bbsRepository.deleteAll();
        replyRepository.deleteAll();
    }

    @Test
    public void 댓글1개_생성(){
        //given, when
        BBS board1 = create_board("게시판1");
        Post post1 = create_post("테스트글1", "안녕하세요", board1);
        Reply reply1 = create_reply("댓글1", post1);

        //then
        System.out.println("게시판: " + board1.getTitle());
        board1.getPosts().stream()
                .forEach(p -> System.out.println("게시글 제목: " + p.getTitle() + ", " + "게시글 내용: " + p.getContent()));
        post1.getReplies().stream()
                .forEach(r -> System.out.println("댓글: " +  r.getContent()));
    }

    @Test
    public void 댓글n개_생성(){
        int reply_length = 10;

        //given, when
        BBS board1 = create_board("게시판1");
        Post post1 = create_post("테스트글1", "안녕하세요", board1);
        for (int i = 0; i < reply_length; i++) {
            String content = "댓글" + Integer.toString(i);
            create_reply(content, post1);
        }

        //then
        System.out.println("게시판: " + board1.getTitle());
        board1.getPosts().stream()
                .forEach(p -> System.out.println("게시글 제목: " + p.getTitle() + ", " + "게시글 내용: " + p.getContent()));
        post1.getReplies().stream()
                .forEach(r -> System.out.println("댓글: " +  r.getContent()));
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