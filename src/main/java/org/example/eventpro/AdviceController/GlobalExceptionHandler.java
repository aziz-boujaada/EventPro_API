package org.example.eventpro.AdviceController;

import jakarta.servlet.http.HttpServletRequest;
import org.example.eventpro.Exception.ResourceNotFoundException;
import org.example.eventpro.dto.ApiErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<ApiErrorResponseDto> buildResponse(
            HttpServletRequest request ,
            HttpStatus status ,
            String message
    ){
        ApiErrorResponseDto apiErrorResponseDto = new ApiErrorResponseDto(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                request.getRequestURI()
                );
        return  ResponseEntity.status(status).body(apiErrorResponseDto);
    }

    /// handle exception of user not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponseDto> handleResourceNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ){
        return  buildResponse(request , HttpStatus.NOT_FOUND , exception.getMessage()) ;
    }
}
