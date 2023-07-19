package com.ELK_POC.elasticSearch.exception;

import com.ELK_POC.elasticSearch.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = IllegalRequestParamException.class)
    private ResponseEntity<ErrorResponse> handleIllegalApiParamException(IllegalRequestParamException e) {
        var message = "Exception API Param from GlobalExceptionHandler, " + e.getMessage();
        LOG.warn(message);

        var errorResponse = new ErrorResponse(message, LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
