package myproject.record_garden.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TodoListDTO {
    private Long id;
    private String content;
    private boolean completed;
}
