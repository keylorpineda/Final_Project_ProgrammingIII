package finalprojectprogramming.project.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import finalprojectprogramming.project.exceptions.api.ApiError;
import finalprojectprogramming.project.exceptions.api.ApiValidationError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request, null);
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiError> handleBusinessRuleException(BusinessRuleException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage(), request, null);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiError> handleInvalidPasswordException(InvalidPasswordException ex,
            HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        List<ApiValidationError> validationErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> new ApiValidationError(error.getField(), resolveMessage(error.getDefaultMessage())))
                .collect(Collectors.toList());

        validationErrors.addAll(ex.getBindingResult().getGlobalErrors().stream()
                .map(error -> new ApiValidationError(error.getObjectName(), resolveMessage(error.getDefaultMessage())))
                .collect(Collectors.toList()));

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, validationErrors);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ApiError> handleCustomValidation(ValidationException ex, HttpServletRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request, ex.getValidationErrors());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex,
            HttpServletRequest request) {
        List<ApiValidationError> validationErrors = ex.getConstraintViolations().stream()
                .map(violation -> new ApiValidationError(violation.getPropertyPath().toString(),
                        resolveMessage(violation.getMessage())))
                .collect(Collectors.toList());

        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", request, validationErrors);
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class,
            HttpMessageNotReadableException.class })
    public ResponseEntity<ApiError> handleBadRequest(Exception ex, HttpServletRequest request) {
        String message;
        if (ex instanceof HttpMessageNotReadableException) {
            message = "Malformed request payload";
        } else if (ex instanceof MissingServletRequestParameterException missing) {
            message = String.format("Missing required parameter '%s'", missing.getParameterName());
        } else if (ex instanceof MethodArgumentTypeMismatchException mismatch) {
            String requiredTypeName = (mismatch.getRequiredType() != null) ? mismatch.getRequiredType().getSimpleName() : "unknown";
            message = String.format("Parameter '%s' should be of type %s", mismatch.getName(), requiredTypeName);
        } else {
            message = resolveMessage(ex.getMessage());
        }
        return buildResponse(HttpStatus.BAD_REQUEST, message, request, null);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrityViolation(DataIntegrityViolationException ex,
            HttpServletRequest request) {
        LOGGER.error("Data integrity violation", ex);
        return buildResponse(HttpStatus.CONFLICT, "Data integrity violation", request, null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        LOGGER.error("Unhandled exception", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request, null);
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message, HttpServletRequest request,
            List<ApiValidationError> validationErrors) {
        ApiError error = new ApiError(status, message, request.getRequestURI(), validationErrors);
        return ResponseEntity.status(status).body(error);
    }

    private String resolveMessage(String message) {
        if (message == null) {
            return null;
        }
        return message.strip().replaceAll("\\.$", "").trim();
    }
}
