package myproject.record_garden.repository;

import lombok.RequiredArgsConstructor;
import myproject.record_garden.dto.DiaryDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DiaryRepository {

    private final SqlSessionTemplate sql;

    public void save(DiaryDTO diaryDTO) {
        sql.insert("Diary.save", diaryDTO);
    }

    public List<DiaryDTO> findAll() {
        return sql.selectList("Diary.findAll");
    }

    // 조회수
    public void updateHits(Long id) {
        sql.update("Diary.updateHits", id);
    }

    public DiaryDTO findById(Long id) {
        return sql.selectOne("Diary.findById", id);
    }

    public DiaryDTO findTodayDiary(Long memberId) {
        return sql.selectOne("Diary.findTodayDiary", memberId);
    }
}
