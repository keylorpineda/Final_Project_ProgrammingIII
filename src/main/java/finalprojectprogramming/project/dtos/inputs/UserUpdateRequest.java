package finalprojectprogramming.project.dtos.inputs;

import finalprojectprogramming.project.models.enums.UserRole;
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
public class UserUpdateRequest {

    private String name;
    private String email;
    private Boolean active;
    private UserRole role;
    private String password;
}