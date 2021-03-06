package study.board.domain.dto.index;

import lombok.Getter;
import study.board.domain.Post;
import java.time.format.DateTimeFormatter;

@Getter
public class Response_index_postview_dto {
    private Long id;
    private String title;
    private Long hit;
    private String content;
    private String created;

    public Response_index_postview_dto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.hit = post.getHit();
        this.content = post.getContent();
        this.created = post.getCreateDate().format(DateTimeFormatter.ofPattern("yy-mm-dd"));
    }
}
