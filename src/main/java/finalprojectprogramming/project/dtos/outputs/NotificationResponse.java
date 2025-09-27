package finalprojectprogramming.project.dtos.outputs;

import finalprojectprogramming.project.models.enums.NotificationStatus;
import finalprojectprogramming.project.models.enums.NotificationType;
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
public class NotificationResponse {

    private Long id;
    private NotificationType type;
    private String sentTo;
    private LocalDateTime sentAt;
    private NotificationStatus status;
}