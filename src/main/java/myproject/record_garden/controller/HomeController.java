package myproject.record_garden.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // 홈에서 한줄일기 작성 및 저장
    @PostMapping("/diary/home-save")
    public String saveFromHome(@ModelAttribute DiaryDTO diaryDTO,
                               RedirectAttributes redirectAttributes) {
        log.debug("선택한 감정: {}", diaryDTO.getMoodToday());
        diaryService.save(diaryDTO);

        // 저장한 오늘의 일기를 다시 가져오기
        DiaryDTO todayDiary = diaryService.findTodayDiary();

        // moodToday 값 이모지로 변환해서 저장
        if (todayDiary != null) {
            todayDiary.setMoodToday(converToEmoji(todayDiary.getMoodToday()));
        }

        // RedirectAttributes에 추가
        redirectAttributes.addFlashAttribute("todayDiary", todayDiary);

        return "redirect:/"; //  PRG패턴 적용(Post -> Redirect -> Get)
    }

    protected String converToEmoji(String mood) {
        // 영어 키워드를 이모지로 매핑하는 map 생성
        Map<String, String> moodMap = Map.of(
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
