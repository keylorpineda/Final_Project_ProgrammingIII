package finalprojectprogramming.project.dtos.inputs;

import com.fasterxml.jackson.databind.JsonNode;
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
public class ReservationCreateRequest {

    private Long userId;
    private Long spaceId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer attendees;
    private String notes;
    private JsonNode weatherCheck;
}