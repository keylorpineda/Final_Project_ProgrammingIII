package finalprojectprogramming.project.dtos.inputs;

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
public class RatingCreateRequest {

    private Long reservationId;
    private Integer score;
    private String comment;
}