package finalprojectprogramming.project.exceptions.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

/**
 * Standard error response returned by the API when an exception occurs.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {

    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;
    private final List<ApiValidationError> validationErrors;

    public ApiError(HttpStatus httpStatus, String message, String path,
            List<ApiValidationError> validationErrors) {
        this.timestamp = LocalDateTime.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.validationErrors = validationErrors == null ? List.of() : List.copyOf(validationErrors);
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<ApiValidationError> getValidationErrors() {
        return validationErrors;
    }
}