package finalprojectprogramming.project.dtos.outputs;


import finalprojectprogramming.project.models.enums.SpaceType;
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
public class SpaceSummaryDto {

    private Long id;
    private String name;
    private SpaceType type;
    private Integer capacity;
    private Boolean active;
    private Float averageRating;
    private LocalDateTime updatedAt;
}