package study.board.service.bbs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import study.board.domain.BBS;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BBS_GeneralServiceTest {
    @Autowired
    BBS_GeneralService bbs_generalService;

    @Test
    public void 게시판생성(){
        BBS board = create_board("테스트 제목", "테스트 내용");
    }

    private BBS create_board(String title, String content){
        //given
        BBS new_bbs = BBS.builder()
                .title(title)
                .content(content)
                .build();

        //when
        Long saveId = bbs_generalService.save(new_bbs);
        // 게시판 중복
        if(saveId == -1) throw new IllegalStateException("이미 존재하는 게시판");

        //then
        LocalDateTime now = LocalDateTime.now();
        BBS find_bbs = bbs_generalService.findById(saveId);
        assertThat(find_bbs.getId()).isEqualTo(new_bbs.getId());
        assertThat(find_bbs.getTitle()).isEqualTo(new_bbs.getTitle());
        assertThat(find_bbs.getContent()).isEqualTo(new_bbs.getContent());
        assertThat(find_bbs.getCreateDate()).isBefore(now);
        assertThat(find_bbs.getLastModifiedDate()).isBefore(now);

        return find_bbs;
    }

}