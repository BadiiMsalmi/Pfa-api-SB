package com.tekup.pfaapisb.Handler;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static com.tekup.pfaapisb.Handler.BusinessErrorCodes.ACCOUNT_LOCKED;
import static com.tekup.pfaapisb.Handler.BusinessErrorCodes.BAD_CREDENTIALS;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExceptionResponse> handleDataIntegrityViolationException(DataIntegrityViolationException exp) {
        // Check the exception message or other context to distinguish between cases if needed
        if (exp.getMessage().contains("Email is already in use")) {
            return ResponseEntity
                    .status(BusinessErrorCodes.EMAIL_ALREADY_USED.getHttpStatus())
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.EMAIL_ALREADY_USED.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.EMAIL_ALREADY_USED.getDescription())
                                    .error("Email is already in use")
                                    .build()
                    );
        } else if (exp.getMessage().contains("Role not supported")) {
            return ResponseEntity
                    .status(BusinessErrorCodes.ROLE_NOT_SUPPORTED.getHttpStatus())
                    .body(
                            ExceptionResponse.builder()
                                    .businessErrorCode(BusinessErrorCodes.ROLE_NOT_SUPPORTED.getCode())
                                    .businessErrorDescription(BusinessErrorCodes.ROLE_NOT_SUPPORTED.getDescription())
                                    .error("Role not supported")
                                    .build()
                    );
        }
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .error("Unknown Data Integrity Violation")
                                .build()
                );
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ExceptionResponse> handleException(LockedException exp) {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(ACCOUNT_LOCKED.getCode())
                                .businessErrorDescription(ACCOUNT_LOCKED.getDescription())
                                .error(exp.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionResponse> handleException() {
        return ResponseEntity
                .status(UNAUTHORIZED)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorCode(BAD_CREDENTIALS.getCode())
                                .businessErrorDescription(BAD_CREDENTIALS.getDescription())
                                .error("Login and / or Password is incorrect")
                                .build()
                );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    //var fieldName = ((FieldError) error).getField();
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                    System.out.println("Validation error: " + errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }




    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exp) {
        exp.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionResponse.builder()
                                .businessErrorDescription("Internal error, please contact the admin")
                                .error(exp.getMessage())
                                .build()
                );
    }
}
