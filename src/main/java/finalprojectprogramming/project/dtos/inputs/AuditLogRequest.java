    package finalprojectprogramming.project.dtos.inputs;

import com.fasterxml.jackson.databind.JsonNode;
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
public class AuditLogRequest {

    private Long userId;
    private String action;
    private String entityId;
    private JsonNode details;
}