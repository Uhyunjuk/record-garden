package myproject.record_garden.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final DiaryService diaryService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @PostMapping("/")
    public String save(DiaryDTO diaryDTO) {
        log.debug("DiaryDTO={}", diaryDTO);
        diaryService.save(diaryDTO);
        return "home";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<DiaryDTO> diaryDTOList = diaryService.findAll();
        model.addAttribute("diaryList", diaryDTOList);
        log.debug("DiaryList={}", diaryDTOList);
        return "list";
    }
}
