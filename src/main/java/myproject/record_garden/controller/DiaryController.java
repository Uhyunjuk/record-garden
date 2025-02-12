package myproject.record_garden.controller;

import lombok.RequiredArgsConstructor;
import myproject.record_garden.service.DiaryService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

}
