package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import study.board.domain.dto.index.Response_index_boards_dto;
import study.board.service.bbs.BBS_IndexService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class Default_Controller {
    private final BBS_IndexService bbs_indexService;

    @GetMapping("/")
    public String index(Model model){
        List<Response_index_boards_dto> boards = bbs_indexService.findAll();

        model.addAttribute("boards", boards);

        return "index";
    }
}
