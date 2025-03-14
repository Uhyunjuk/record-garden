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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DiaryController {

    private final DiaryService diaryService;

    // 비회원 한줄일기 게시판
    @GetMapping("/diary/list")
    public String diaryListBoard() {

        return "/member/login";
    }

    // 회원 한줄일기 목록
    @GetMapping("/diary/list/{memberId}")
    public String diaryList(@PathVariable("memberId") Long memberId,
                            Model model,
                            HttpSession session) {

        // 세션에서 회원 정보 가져오기
        MemberDTO loginUser = (MemberDTO) session.getAttribute("loginUser");

        // 로그인 안했을때 예외처리
        if (loginUser == null) {
            return "/member/login";
        }
        // 회원의 일기만 조회하도록 예외처리
        if (!loginUser.getMemberId().equals(memberId)) {
            return "redirect:/diary/list" + loginUser.getMemberId();
        }

        // 로그인한 회원의 일기 목록 가져오기
        List<DiaryDTO> diaryList = diaryService.findAllByMemberId(memberId);

        // moodToday 값을 이모티콘으로 변환해서 저장
        for (DiaryDTO diary : diaryList) {
            diary.setMoodToday(converToEmoji(diary.getMoodToday()));
        }

        // 변환된 데이터를 모델에 추가해서 타임리프에서 사용가능하도록 함
        model.addAttribute("diaryList", diaryList);
        log.debug("DiaryList={}", diaryList);

        return "diary/list";
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

//    // 회원 한줄일기 상세조회
//    @GetMapping("{id}")
//    public String findById(@PathVariable("id") Long id, Model model) {
//        // 조회수 처리
//        diaryService.updateHits(id);
//        // 상세 내용 가져오기
//        DiaryDTO diaryDTO = diaryService.findById(id);
//        log.debug("diaryDTO = {}", diaryDTO);
//        model.addAttribute("diary", diaryDTO);
//        return "diary/detail";
//    }
}
