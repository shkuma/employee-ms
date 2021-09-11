package com.example.employeems.exceptions;

import com.example.employeems.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionToResponseErrorHandler extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionToResponseErrorHandler.class);

    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handleException(HttpServletRequest req, Exception exception) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = "Internal Server Error";
        String code = "";
        Exception exp = null;
        if (exception instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            message = exception.getMessage();
            code = "404";
        } else if(exception instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
            message = exception.getMessage();
            code = "400";
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            message = exception.getMessage();
            code = "500";
        }
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        LOGGER.error("Error: {}",errorResponse);
        return new ResponseEntity<ErrorResponse>(errorResponse, status);
    }

}
