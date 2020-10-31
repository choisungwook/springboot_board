package study.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


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

    private String content;


    @Builder
    public BBS(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void modify_title(String title){
        this.title = title;
    }

    public void modify_content(String content){
        this.content = content;
    }
}
