package study.board.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.BBS;
import study.board.domain.Post;
import study.board.domain.Reply;
import study.board.service.bbs.BBS_GeneralService;
import study.board.service.post.Post_GeneralService;
import study.board.service.reply.Reply_GeneralService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitDB {
    private final InitService initService;

    @PostConstruct
    public void run(){
        initService.run();
    }

    @Component
    @RequiredArgsConstructor
    static class InitService{
        private final Post_GeneralService post_generalService;
        private final BBS_GeneralService bbs_generalService;
        private final Reply_GeneralService reply_generalService;

        @Transactional
        public void run(){
            init1();
            init2();
        }

        // 게시판1
        public void init1(){
            int reply_length = 10;

            //given, when
            BBS board = create_board("게시판1");
            Post post = create_post("테스트글1", "안녕하세요", board);
            for (int i = 0; i < reply_length; i++) {
                String content = "댓글" + Integer.toString(i);
                create_reply(content, post);
            }
        }

        // 게시판2
        public void init2(){
            int reply_length = 10;

            //given, when
            BBS board = create_board("게시판2");
            Post post = create_post("테스트글1", "안녕하세요", board);
            for (int i = 0; i < reply_length; i++) {
                String content = "댓글" + Integer.toString(i);
                create_reply(content, post);
            }
        }

        private Post create_post(String title, String content, BBS bbs){
            Post new_post = Post.builder()
                    .title(title)
                    .content(content)
                    .bbs(bbs)
                    .build();
            Long saveId = post_generalService.save(new_post);
            Post find_post = post_generalService.findById(saveId);

            return find_post;
        }

        private BBS create_board(String title){
            //given
            BBS new_bbs = BBS.builder()
                    .title(title)
                    .build();

            Long saveId = bbs_generalService.save(new_bbs);

            LocalDateTime now = LocalDateTime.now();
            BBS find_bbs = bbs_generalService.findById(saveId);

            return find_bbs;
        }

        private Reply create_reply(String content, Post post){
            Reply new_reply = Reply.builder()
                    .content(content)
                    .post(post)
                    .build();

            Long saveId = reply_generalService.save(new_reply);
            Reply find_reply = reply_generalService.findById(saveId);

            return find_reply;
        }
    }
}
