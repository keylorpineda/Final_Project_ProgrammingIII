package finalprojectprogramming.project.dtos.outputs;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SettingResponse {

    private Long id;
    private String key;
    private String value;
    private String description;
    private LocalDateTime updatedAt;
}