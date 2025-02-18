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
import java.util.Map;

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
        // 저장된 diary list 가져오기
        List<DiaryDTO> diaryList = diaryService.findAll();

        // moodToday 값을 이모티콘으로 변환해서 저장
        for (DiaryDTO diary : diaryList) {
            diary.setMoodToday(converToEmoji(diary.getMoodToday()));
        }

        // 변환된 데이터를 모델에 추가해서 타임리프에서 사용가능하도록 함
        model.addAttribute("diaryList", diaryList);
        log.debug("DiaryList={}", diaryList);
        return "diary/diaryList";
    }

    private String converToEmoji(String mood) {
        // 영어 키워드를 이모지로 매핑하는 map 생성
        Map<String, String>  moodMap = Map.of(
                "happy", "\uD83D\uDE0A",
                "sad", "\uD83D\uDE22",
                "angry", "\uD83D\uDE21",
                "excited", "\uD83E\uDD29",
                "tired", "\uD83D\uDE34",
                "calm", "\uD83D\uDE0C",
                "soso", "\uD83D\uDE42"
        );
        // mood 값이 있으면 해당 이모지 반환, 없으면 물음표 반환
        return moodMap.getOrDefault(mood, "?");
    }
}
