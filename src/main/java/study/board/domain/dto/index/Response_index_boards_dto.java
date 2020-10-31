package study.board.domain.dto.index;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Response_index_boards_dto {
    private Long id;
    private String title;

    @Builder
    public Response_index_boards_dto(Long id, String title) {
        this.id = id;
        this.title = title;
    }
}
