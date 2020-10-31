package study.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;


/***
 * 게시판 Entity
 */
@Entity
@Table(name = "BBS")
@NoArgsConstructor
@Getter
public class BBS extends TimeBaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bbs_id")
    private Long id;

    private String title;

    @OneToMany(mappedBy = "bbs")
    List<Post> posts = new LinkedList<>();

    @Builder
    public BBS(String title) {
        this.title = title;
    }

    public void add_post(Post post){
        posts.add(post);
    }

    public void modify_title(String title){
        this.title = title;
    }
}
