package com.wemakeprice.parser.web;


import com.wemakeprice.parser.error.ServiceError;
import com.wemakeprice.parser.error.ServiceException;
import com.wemakeprice.parser.web.dto.ResultError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ParserExceptionHandler {


    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> serviceExceptionHandler(ServiceException e) {
        return new ResponseEntity<>(e.getServiceError().getResultError(), e.getServiceError().getResultError().getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> responseGlobalException(Exception e) {
        log.error("Exception:", e);
        return new ResponseEntity<>(ServiceError.SERVICE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> httpMsgConverterValidationHanlder(MethodArgumentNotValidException e) {
        return responseValidException(e.getBindingResult());
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> modelAttributeValidationHanlder(BindException e) {
        return responseValidException(e.getBindingResult());
    }

    private ResponseEntity<?> responseValidException(BindingResult bindingResult) {
        List<ResultError.FieldValue> fieldValues = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            ResultError.FieldValue fieldValue = ResultError.FieldValue
                    .builder()
                    .field(fieldError.getField())
                    .value(fieldError.getRejectedValue())
                    .reason(fieldError.getDefaultMessage())
                    .build();
            fieldValues.add(fieldValue);
        }

        ResultError response = ResultError.builder()
                .code(ServiceError.REQUEST_VALIDATION.getCode())
                .message(ServiceError.REQUEST_VALIDATION.getMessage())
                .httpStatus(ServiceError.REQUEST_VALIDATION.getHttpStatus())
                .fieldValues(fieldValues)
                .build();
        return new ResponseEntity(response, response.getHttpStatus());
    }
}
