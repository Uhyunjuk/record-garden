package myproject.record_garden.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.dto.MemberDTO;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final DiaryService diaryService;

    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        // 로그인한 경우만 오늘의 일기 조회
        if (loginUser != null) {
            DiaryDTO todayDiary = diaryService.findTodayDiary(loginUser.getMemberId());

            // 오늘 작성한 일기가 있다면 이모지 변환 후 모델에 추가
            if (todayDiary != null) {
                todayDiary.setMoodToday(converToEmoji(todayDiary.getMoodToday()));
                log.debug("오늘의 일기 조회: {}", todayDiary);
            }

            model.addAttribute("todayDiary", todayDiary);
        }
        return "home";
    }

    // 회원 한줄일기 작성
    @PostMapping("/diary/write")
    public String diaryWrite(@ModelAttribute DiaryDTO diaryDTO,
                             HttpSession session,
                             Model model) {
        log.debug("[일기 작성 요청] diaryDTO (Before) : {}", diaryDTO);

        // 세션에서 로그인한 회원정보 가져오기
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);

        // 일기 작성자의 닉네임을 세팅
        diaryDTO.setDiaryWriter(loginUser.getMemberNickname());
        diaryDTO.setMemberId(loginUser.getMemberId());

        // 오늘의 기분을 이모지로 변환 후 저장
        diaryDTO.setMoodToday(converToEmoji(diaryDTO.getMoodToday()));

        log.debug("[일기 작성 요청] diaryDTO (After) : {}", diaryDTO);

        // 한줄일기 저장
        diaryService.save(diaryDTO);

        // 리다이렉트 새로고침 시 todayDiary가 갱신됨
        return "redirect:/";
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
