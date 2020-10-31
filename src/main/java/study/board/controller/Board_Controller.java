package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import study.board.domain.dto.index.Response_index_boards_dto;
import study.board.domain.dto.index.Response_index_posts_dto;
import study.board.service.bbs.BBS_IndexService;
import study.board.service.post.Post_IndexService;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class Board_Controller {
    private final BBS_IndexService bbs_indexService;
    private final Post_IndexService post_indexService;

    @GetMapping("/board/{id}")
    public String list(@PathVariable Long id, Model model){
        List<Response_index_boards_dto> boards = bbs_indexService.findAll();
        List<Response_index_posts_dto> posts = post_indexService.findAll();
        String board_title = bbs_indexService.findTitleById(id);

        model.addAttribute("board_title", board_title);
        model.addAttribute("boards", boards);
        model.addAttribute("posts", posts);

        return "board_list";
    }
}
