package study.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "REPLY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends TimeBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Reply(String content, Post post) {
        this.content = content;

        // 댓글<->게시글 연관관계 설정
        this.post = post;
        post.add_reply(this);
    }
}
