package myproject.record_garden.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    public String save(@ModelAttribute DiaryDTO diaryDTO) {
        log.debug("선택한 감정: {}", diaryDTO.getMoodToday());
        diaryService.save(diaryDTO);
        return "redirect:/";
    }

    @GetMapping("/list")
    public String findAll(Model model) {
        List<DiaryDTO> diaryList = diaryService.findAll();
        model.addAttribute("diaryList", diaryList);
        log.debug("DiaryList={}", diaryList);
        return "diary/diaryList";
    }
}
