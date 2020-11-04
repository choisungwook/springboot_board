package study.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bbs_id")
    private BBS bbs;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Reply> replies = new LinkedList<>();

    @Builder
    public Post(String title, String content, BBS bbs) {
        this.title = title;
        this.content = content;
        this.hit = 0L;

        // 게시글<->게시판 연관관계 설정
        if(bbs != null){
            this.bbs = bbs;
            bbs.add_post(this);
        }
    }

    // 게시글<->댓글 연관관계 설정
    public void add_reply(Reply reply){
        this.replies.add(reply);
    }

    public void delete_reply(Reply reply){
        this.replies.remove(reply);
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
