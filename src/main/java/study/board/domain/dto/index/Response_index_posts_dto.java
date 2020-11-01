package study.board.domain.dto.index;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import org.springframework.data.jpa.repository.Query;
import study.board.domain.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class Response_index_posts_dto {
    private Long id;
    private String title;
    private Long hit;
    private String created;

    @QueryProjection
    public Response_index_posts_dto(Long id, String title, Long hit, LocalDateTime created) {
        this.id = id;
        this.title = title;
        this.hit = hit;
        this.created = created.format(DateTimeFormatter.ofPattern("yy-mm-dd"));
    }
}
