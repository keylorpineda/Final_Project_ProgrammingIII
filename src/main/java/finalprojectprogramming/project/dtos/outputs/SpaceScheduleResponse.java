package finalprojectprogramming.project.dtos.outputs;


import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
public class SpaceScheduleResponse {

    private Long id;
    private DayOfWeek dayOfWeek;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Boolean holidayOverride;
    private String maintenanceNotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}