package com.hospital.backendHospital.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Recurso no encontrado");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleInvalidDataException(InvalidDataException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Datos inválidos");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleGenericException(Exception ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Error interno del servidor");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public Map<String, String> handlerMaxSizeException (MaxUploadSizeExceededException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "El archivo excede los 2MB permitidos");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handlerFileNotFoundException (FileNotFoundException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "El archivo solicitado no se ha encontrado o no esta disponible");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handlerIOException (IOException ex) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("error", "Error al procesar el archivo");
        errorMap.put("detalle", ex.getMessage());
        return errorMap;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

}