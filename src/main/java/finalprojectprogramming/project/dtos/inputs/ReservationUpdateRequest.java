package finalprojectprogramming.project.dtos.inputs;

import com.fasterxml.jackson.databind.JsonNode;
import finalprojectprogramming.project.models.enums.ReservationStatus;
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
public class ReservationUpdateRequest {

    private ReservationStatus status;
    private Integer attendees;
    private String notes;
    private Long approvedById;
    private LocalDateTime canceledAt;
    private LocalDateTime checkinAt;
    private JsonNode weatherCheck;
}