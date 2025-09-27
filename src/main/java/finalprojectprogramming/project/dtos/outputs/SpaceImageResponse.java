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
public class SpaceImageResponse {

    private Long id;
    private String imageUrl;
    private String description;
    private Boolean active;
    private Integer displayOrder;
    private LocalDateTime uploadedAt;
}