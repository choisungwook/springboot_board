package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import study.board.domain.dto.index.Response_index_boards_dto;
import study.board.domain.dto.index.Response_index_posts_dto;
import study.board.service.bbs.BBS_IndexService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Board_Controller {
    private final BBS_IndexService bbs_indexService;

    @GetMapping("/board/{id}")
    public String list(@PathVariable Long id, @RequestParam(value = "searchpage", defaultValue = "1") int page, Model model){
        int offset = 20;

        // 페이징 유효성 검사
        page = page -1;
        if(page < 0) page = 0;
        PageRequest pageRequest = PageRequest.of(page, offset);

        List<Response_index_boards_dto> boards = bbs_indexService.findAll();
        Page<Response_index_posts_dto> posts = bbs_indexService.findPosts(id, pageRequest);
        String board_title = bbs_indexService.findTitleById(id);

        // 페이징 마커
        int pageMarker_size = 10;
        int from = ((posts.getNumber()) / pageMarker_size) * pageMarker_size + 1;
        int to = from + pageMarker_size - 1;
        // 페이지 마커 끝 유효성 검사
        if(to > posts.getTotalPages()) to = posts.getTotalPages();

        model.addAttribute("board_title", board_title);
        model.addAttribute("board_id", id);
        model.addAttribute("boards", boards);
        model.addAttribute("posts", posts);
        model.addAttribute("from", from);
        model.addAttribute("to", to);

        return "board_list";
    }
}
