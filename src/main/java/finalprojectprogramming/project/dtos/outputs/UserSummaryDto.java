package finalprojectprogramming.project.dtos.outputs;

import finalprojectprogramming.project.models.enums.UserRole;
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
public class UserSummaryDto {

    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private Boolean active;
    private LocalDateTime lastLoginAt;
}