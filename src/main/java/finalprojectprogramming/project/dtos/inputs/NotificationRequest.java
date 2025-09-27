package finalprojectprogramming.project.dtos.inputs;

import finalprojectprogramming.project.models.enums.NotificationStatus;
import finalprojectprogramming.project.models.enums.NotificationType;
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
public class NotificationRequest {

    private Long reservationId;
    private NotificationType type;
    private String sentTo;
    private NotificationStatus status;
}