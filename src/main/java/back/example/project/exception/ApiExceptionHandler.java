package back.example.project.exception;

import back.example.project.dto.ErrorDto;
import back.example.project.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(annotations = ControllerExceptionHandler.class)
public class ApiExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponseDto> handleCreationException(ApiException exception) {
        var error = ErrorDto.builder()
                .message(exception.getMessage())
                .build();

        var result = new ErrorResponseDto().setError(error);

        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(result);
    }
    
}