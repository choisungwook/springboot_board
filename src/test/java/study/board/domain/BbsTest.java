package study.board.domain;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class BbsTest {

    @Test
    public void 게시판Entity_생성(){
        BBS new_Member = BBS.builder()
                .content("test content")
                .title("test title")
                .build();
    }
}