package chui.exception;

import chui.model.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponseDto> handleValidationException(ValidationException ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        ex.getDetails(),
        "400"
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }

  @ExceptionHandler(SongMetadataNotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleSongMetadataNotFoundException(
      SongMetadataNotFoundException ex) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "404"));
  }

  @ExceptionHandler(SongMetadataAlreadyExistsException.class)
  public ResponseEntity<ErrorResponseDto> handleSongMetadataAlreadyExistsException(
      SongMetadataAlreadyExistsException ex) {
    ErrorResponseDto errorResponse = new ErrorResponseDto(
        ex.getMessage(),
        null,
        "409"
    );
    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
  }
}
