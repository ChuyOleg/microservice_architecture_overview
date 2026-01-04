package chui.exception;

import chui.model.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(InvalidMp3Exception.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidMp3Exception(InvalidMp3Exception ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "400"));
  }

  @ExceptionHandler(ResourceServiceException.class)
  public ResponseEntity<ErrorResponseDto> handleResourceServiceException(ResourceServiceException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "500"));
  }

  @ExceptionHandler(InvalidIdException.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidIdException(InvalidIdException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "400"));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponseDto> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "404"));
  }

  @ExceptionHandler(InvalidCsvException.class)
  public ResponseEntity<ErrorResponseDto> handleInvalidCsvException(InvalidCsvException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .contentType(MediaType.APPLICATION_JSON)
        .body(new ErrorResponseDto(ex.getMessage(), null, "400"));
  }
}
