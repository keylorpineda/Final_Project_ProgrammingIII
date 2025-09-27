package finalprojectprogramming.project.dtos.inputs;

import finalprojectprogramming.project.models.enums.SpaceType;
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
public class SpaceCreateRequest {

    private String name;
    private SpaceType type;
    private Integer capacity;
    private String description;
    private String location;
    private Integer maxReservationDuration;
    private Boolean requiresApproval;
    private List<SpaceImageRequest> images;
    private List<SpaceScheduleRequest> schedules;
}