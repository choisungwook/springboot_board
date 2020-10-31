package study.board.service.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Reply;
import study.board.repository.ReplyRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class Reply_GeneralService {
    private final ReplyRepository replyRepository;

    @Transactional
    public Long save(Reply reply){
        return replyRepository.save(reply).getId();
    }

    public Reply findById(Long id){
        Reply find_reply = replyRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 댓글")
                );

        return find_reply;
    }
}
