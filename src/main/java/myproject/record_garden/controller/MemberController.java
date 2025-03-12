package myproject.record_garden.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myproject.record_garden.dto.MemberDTO;
import myproject.record_garden.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @GetMapping("/login")
    public String login() {
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session,
                        Model model) {

        try {
            MemberDTO loginUser = memberService.login(memberDTO);

            if (loginUser != null) {
                session.setAttribute("loginUser", loginUser); // 전체 회원 객체 저장
                log.debug("로그인 성공 후 반환: {}", loginUser);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "redirect:/";
    }

    // 회원가입
    @GetMapping("/signup")
    public String signup() {
        return "member/signup";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "redirect:/member/login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 무효화
        return "redirect:/";  // 홈 화면으로 이동
    }
}
