package myproject.record_garden.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DiaryDTO {
    private Long id;
    private String diaryWriter;
    private String diaryContents;
    private String moodToday;
    private int diaryHits;
    private String createdAt;
}
