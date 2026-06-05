package com.streisky.precoreal.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata {@link NoSuchElementException} retornando HTTP 404.
     *
     * @param ex exceção lançada quando o recurso não é encontrado
     * @return mapa com a mensagem de erro
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(NoSuchElementException ex) {
        return Map.of("error", messageOf(ex));
    }

    /**
     * Trata {@link IllegalStateException} retornando HTTP 400.
     *
     * @param ex exceção lançada por estado inválido na requisição
     * @return mapa com a mensagem de erro
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBadRequest(IllegalStateException ex) {
        return Map.of("error", messageOf(ex));
    }

    /**
     * Trata {@link RuntimeException} não mapeadas retornando HTTP 500.
     *
     * @param ex exceção inesperada em tempo de execução
     * @return mapa com a mensagem de erro
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleRuntime(RuntimeException ex) {
        return Map.of("error", messageOf(ex));
    }

    private String messageOf(Exception ex) {
        return ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();
    }
}
