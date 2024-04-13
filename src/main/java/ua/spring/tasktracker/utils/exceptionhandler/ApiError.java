package ua.spring.tasktracker.utils.exceptionhandler;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Error response")
public class ApiError {
    @Schema(description = "Timestamp of the error")
    private LocalDateTime timestamp;
    @Schema(description = "HTTP status code", example = "BAD_REQUEST")
    private HttpStatus statusCode;
    @Schema(description = "Error message", example = "Validation failed")
    private String message;
    @Schema(description = "Error code", example = "1")
    private Integer errorCode;
    @Schema(description = "List of errors", example = "[\"Name can't be empty\"]")
    private List<String> errors;
}
