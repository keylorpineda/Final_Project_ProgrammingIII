package finalprojectprogramming.project.dtos.outputs;


import com.fasterxml.jackson.databind.JsonNode;
import finalprojectprogramming.project.dtos.outputs.NotificationResponse;
import finalprojectprogramming.project.dtos.outputs.RatingResponse;
import finalprojectprogramming.project.dtos.outputs.SpaceSummaryDto;
import finalprojectprogramming.project.dtos.outputs.UserSummaryDto;
import finalprojectprogramming.project.models.enums.ReservationStatus;
import java.time.LocalDateTime;
import java.util.List;
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
public class ReservationResponse {

    private Long id;
    private ReservationStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String qrCode;
    private Integer attendees;
    private String notes;
    private LocalDateTime canceledAt;
    private LocalDateTime checkinAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private JsonNode weatherCheck;
    private UserSummaryDto user;
    private SpaceSummaryDto space;
    private UserSummaryDto approvedBy;
    private RatingResponse rating;
    private List<NotificationResponse> notifications;
}