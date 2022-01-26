package com.github.mohammadmasoomi.inventory.exception;

import com.github.mohammadmasoomi.inventory.core.exception.GeneralException;
import com.github.mohammadmasoomi.inventory.core.ontology.InventoryOntology;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class InventoryResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryResponseEntityExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception exception) {
        LOGGER.debug(exception.getMessage(), exception);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.SERVER_INTERNAL_ERROR.getCode());
        jsonObject.addProperty(InventoryOntology.ERROR_MESSAGE, AppErrorMessage.SERVER_INTERNAL_ERROR.getMessage());
        return new ResponseEntity<>(jsonObject, AppErrorMessage.SERVER_INTERNAL_ERROR.getHttpCode());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Object> handleMethodArgumentTypeMismatchExceptions(MethodArgumentTypeMismatchException exception) {
        LOGGER.debug(exception.getMessage(), exception);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.VALIDATION_ERROR.getCode());
        jsonObject.addProperty(InventoryOntology.ERROR_MESSAGE, AppErrorMessage.VALIDATION_ERROR.getMessage());
        return new ResponseEntity<>(jsonObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<Object> handleAllExceptions(GeneralException exception) {
        LOGGER.debug(exception.getMessage(), exception);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InventoryOntology.ERROR_CODE, exception.getCode());
        jsonObject.addProperty(InventoryOntology.ERROR_MESSAGE, exception.getMessage());
        return new ResponseEntity<>(jsonObject, exception.getHttpCode());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<JsonObject> handleAccessDeniedExceptions(AccessDeniedException exception) {
        logger.debug(exception.getMessage(), exception);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InventoryOntology.ERROR_MESSAGE, AppErrorMessage.ACCOUNT_DENIED.getMessage());
        jsonObject.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.ACCOUNT_DENIED.getCode());
        return new ResponseEntity<>(jsonObject, AppErrorMessage.ACCOUNT_DENIED.getHttpCode());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<JsonObject> handleConstraintViolationExceptions(ConstraintViolationException exception) {
        logger.debug(exception.getMessage(), exception);
        JsonObject errorMessagesJsonObject = new JsonObject();
        for (ConstraintViolation constraintViolation : exception.getConstraintViolations()) {
            String propertyPath = constraintViolation.getPropertyPath().toString();
            String fieldName = propertyPath.substring(propertyPath.lastIndexOf(".") + 1);
            String errorMessage = constraintViolation.getMessage();
            errorMessagesJsonObject.addProperty(fieldName, errorMessage);
        }

        JsonObject result = new JsonObject();
        result.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.VALIDATION_ERROR.getCode());
        result.add(InventoryOntology.ERROR_MESSAGE, errorMessagesJsonObject);
        return new ResponseEntity<>(result, AppErrorMessage.VALIDATION_ERROR.getHttpCode());
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        LOGGER.debug(ex.getMessage(), ex);
        JsonObject errorMessagesJsonObject = new JsonObject();
        if (ex.getBindingResult().getFieldErrorCount() != 0) {
            for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
                errorMessagesJsonObject.addProperty(fieldError.getField(), fieldError.getDefaultMessage());
            }

        } else {
            for (ObjectError allError : ex.getBindingResult().getAllErrors()) {
                errorMessagesJsonObject.addProperty("Invalid Input Data", allError.getDefaultMessage());
            }
        }
        JsonObject result = new JsonObject();
        result.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.VALIDATION_ERROR.getCode());
        result.add(InventoryOntology.ERROR_MESSAGE, errorMessagesJsonObject);
        return new ResponseEntity<>(result, AppErrorMessage.VALIDATION_ERROR.getHttpCode());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        LOGGER.debug(ex.getMessage(), ex);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(InventoryOntology.ERROR_CODE, AppErrorMessage.VALIDATION_ERROR.getCode());
        jsonObject.addProperty(InventoryOntology.ERROR_MESSAGE, AppErrorMessage.VALIDATION_ERROR.getMessage());
        return new ResponseEntity<>(jsonObject, AppErrorMessage.VALIDATION_ERROR.getHttpCode());
    }

}
