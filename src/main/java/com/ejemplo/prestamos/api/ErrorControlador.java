package com.ejemplo.prestamos.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ErrorControlador {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> solicitudInvalida(MethodArgumentNotValidException error) {
        String mensaje = error.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(campo -> campo.getField() + ": dato inválido")
            .findFirst()
            .orElse("La solicitud contiene datos inválidos");

        return Map.of("error", mensaje);
    }
}