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
public class SpaceImageRequest {

    private Long id;
    private String imageUrl;
    private String description;
    private Boolean active;
    private Integer displayOrder;
}