package finalprojectprogramming.project.dtos.outputs;

import com.fasterxml.jackson.databind.JsonNode;
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
public class AuditLogResponse {

    private Long id;
    private Long userId;
    private String action;
    private String entityId;
    private JsonNode details;
    private LocalDateTime timestamp;
}