package myproject.record_garden.service;

import lombok.RequiredArgsConstructor;
import myproject.record_garden.dto.DiaryDTO;
import myproject.record_garden.repository.DiaryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;

    public void save(DiaryDTO diaryDTO) {
        diaryRepository.save(diaryDTO);
    }

    public List<DiaryDTO> findAllByMemberId(Long memberId) {
        return diaryRepository.findAllByMemberId(memberId);
    }

    public void updateHits(Long id) {
        diaryRepository.updateHits(id);
    }

    public DiaryDTO findById(Long id) {
        return diaryRepository.findById(id);
    }

    public DiaryDTO findTodayDiary(Long memberId) {
        return diaryRepository.findTodayDiary(memberId);
    }
}
