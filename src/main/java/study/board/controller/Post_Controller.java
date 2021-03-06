package study.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import study.board.domain.dto.index.Response_index_boards_dto;
import study.board.domain.dto.index.Response_index_postview_dto;
import study.board.service.bbs.BBS_IndexService;
import study.board.service.post.Post_indexService;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
@Slf4j
public class Post_Controller {
    private final BBS_IndexService bbs_indexService;
    private final Post_indexService post_indexService;

    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model){
        List<Response_index_boards_dto> boards = bbs_indexService.findAll();
        Response_index_postview_dto postview_dto = post_indexService.click(id);

        model.addAttribute("boards", boards);
        model.addAttribute("post", postview_dto);

        return "post_view";
    }
}
