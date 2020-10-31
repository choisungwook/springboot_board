package study.board.service.bbs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.BBS;
import study.board.domain.dto.index.Response_index_boards_dto;
import study.board.repository.BBSRepository;

import java.util.List;
import java.util.stream.Collectors;

/***
 * 메인(index.html)화면을 위한 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BBS_IndexService {
    private final BBSRepository bbsRepository;

    public List<Response_index_boards_dto> findAll(){
        List<BBS> all = bbsRepository.findAll();
        List<Response_index_boards_dto> response = all.stream()
                .map(board -> new Response_index_boards_dto(board.getId(), board.getTitle()))
                .collect(Collectors.toList());

        return response;
    }

    public String findTitleById(Long id){
        BBS find_board = bbsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않는 게시판")
                );

        return find_board.getTitle();
    }
}
