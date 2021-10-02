package com.project.backend.exceptionhandler;

import com.project.backend.exceptions.IdentityNumberIsAlreadyExistException;
import com.project.backend.exceptions.PhoneNumberIsAlreadyExistException;
import com.project.backend.exceptions.ResourceNotFoundException;
import com.project.backend.model.AppError;
import com.project.backend.repository.AppErrorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final AppErrorRepository appErrorRepository;

    @Autowired
    public GlobalExceptionHandler(AppErrorRepository appErrorRepository) {
        this.appErrorRepository = appErrorRepository;
    }

    private AppError prepareErrorResponse(HttpStatus httpStatus, String message){
        AppError appError = new AppError();
        appError.setMessage(message);
        appError.setStatus(httpStatus.value());
        appError.setTimestamp(System.currentTimeMillis());
        return appError;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<AppError> handleException(ResourceNotFoundException exception){
        AppError appError = prepareErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
        appErrorRepository.save(appError);
        log.error("Exception: " + exception.getMessage());
        return new ResponseEntity<>(appError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IdentityNumberIsAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<AppError> handleException(IdentityNumberIsAlreadyExistException exception){
        AppError appError = prepareErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
        appErrorRepository.save(appError);
        log.error("Exception: " + exception.getMessage());
        return new ResponseEntity<>(appError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PhoneNumberIsAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<AppError> handleException(PhoneNumberIsAlreadyExistException exception){
        AppError appError = prepareErrorResponse(HttpStatus.CONFLICT, exception.getMessage());
        appErrorRepository.save(appError);
        log.error("Exception: " + exception.getMessage());
        return new ResponseEntity<>(appError, HttpStatus.CONFLICT);
    }



}
