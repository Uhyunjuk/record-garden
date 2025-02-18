package myproject.record_garden.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    // 한줄일기 목록

    // 한줄일기 상세조회
    @GetMapping("{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        // 조회수 처리
        diaryService.updateHits(id);
        // 상세 내용 가져오기
        DiaryDTO diaryDTO = diaryService.findById(id);
        log.debug("diaryDTO = {}", diaryDTO);
        model.addAttribute("diary", diaryDTO);
        return "diary/diaryDetail";
    }
}
