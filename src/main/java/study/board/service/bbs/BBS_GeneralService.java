package study.board.service.bbs;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.board.domain.BBS;
import study.board.repository.BBSRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BBS_GeneralService {
    private final BBSRepository bbsRepository;

    public BBS findById(Long id){
        BBS find_bbs = bbsRepository.findById(id)
                .orElseThrow(
                        () -> new IllegalStateException("존재하지 않은 게시판")
                );

        return find_bbs;
    }

    public List<BBS> findAll(){
        return bbsRepository.findAll();
    }

    @Transactional
    public Long save(BBS bbs){
        Long saveId = -1L;

        // 게시판이 중복검사
        if(!isExist(bbs.getTitle())) saveId = bbsRepository.save(bbs).getId();

        return saveId;
    }

    @Transactional
    public void delete(Long id){
        BBS find_post = this.findById(id);

        bbsRepository.delete(find_post);
    }

    private boolean isExist(String title){
        return bbsRepository.existsByTitle(title);
    }
}
