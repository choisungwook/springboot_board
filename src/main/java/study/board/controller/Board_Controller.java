package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public String list(@PathVariable Long id, @RequestParam(value = "searchpage", defaultValue = "0") int page, Model model){
        int offset = 20;
        PageRequest pageRequest = PageRequest.of(page, offset);

        List<Response_index_boards_dto> boards = bbs_indexService.findAll();
        PageImpl<Response_index_posts_dto> posts = bbs_indexService.findPosts(id, pageRequest);
        String board_title = bbs_indexService.findTitleById(id);

        model.addAttribute("board_title", board_title);
        model.addAttribute("boards", boards);
        model.addAttribute("posts", posts);

        return "board_list";
    }
}
