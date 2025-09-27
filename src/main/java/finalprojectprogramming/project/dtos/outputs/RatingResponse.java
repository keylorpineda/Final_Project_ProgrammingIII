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
public class RatingResponse {

    private Long id;
    private Long reservationId;
    private Integer score;
    private String comment;
    private LocalDateTime createdAt;
}