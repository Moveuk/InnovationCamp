package com.ldu.spring_blogcrud.common.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(EntityNotFoundException ex){
        log.error("handleEntityNotFoundException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PostUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handlePostUnauthorizedException(PostUnauthorizedException ex){
        log.error("handlePostUnauthorizedException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(PutUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handlePutUnauthorizedException(PutUnauthorizedException ex){
        log.error("handlePostUnauthorizedException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(DeleteUnauthorizedException.class)
    public ResponseEntity<ErrorResponseDto> handleDeleteUnauthorizedException(DeleteUnauthorizedException ex){
        log.error("handleDeleteUnauthorizedException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(NicknameDuplicatedException.class)
    public ResponseEntity<ErrorResponseDto> nicknameDuplicatedException(NicknameDuplicatedException ex){
        log.error("nicknameDuplicatedException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(UserNotMatchRegexException.class)
    public ResponseEntity<ErrorResponseDto> userNotMatchRegexException(UserNotMatchRegexException ex){
        log.error("userNotMatchRegexException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ex.getErrorCode());
        return new ResponseEntity<>(response, HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> commonIllegalArgumentException(IllegalArgumentException ex){
        log.error("commonIllegalArgumentException",ex);
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(Exception ex){
        log.error("handleException",ex);
        ErrorResponseDto response = new ErrorResponseDto(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
