package study.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "POSTS")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends TimeBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private Long hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bbs_id")
    private BBS bbs;

    @Builder
    public Post(String title, String content, BBS bbs) {
        this.title = title;
        this.content = content;
        this.hit = 0L;

        // 연관관계 설정
        if(bbs != null){
            this.bbs = bbs;
            bbs.add_post(this);
        }
    }

    public void increase_hit(){
        this.hit += 1L;
    }

    public void change_title(String title){
        this.title = title;
    }

    public void change_content(String content){
        this.content = content;
    }
}
