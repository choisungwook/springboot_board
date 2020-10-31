package study.board.service.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.Reply;
import study.board.repository.ReplyRepository;

import java.util.List;

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

    public List<Reply> findAll(){
        return replyRepository.findAll();
    }

    @Transactional
    public void delete(Long id){
        Reply find_reply = this.findById(id);
        find_reply.getPost().delete_reply(find_reply);

        replyRepository.delete(find_reply);
    }
}
