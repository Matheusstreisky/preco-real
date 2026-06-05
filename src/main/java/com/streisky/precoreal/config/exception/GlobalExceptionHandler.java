package com.streisky.precoreal.config.exception;

import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata falhas de validação em {@code @RequestBody} anotados com {@code @Valid}.
     *
     * @param ex exceção com os erros de campo do bean
     * @return mapa de campo → mensagem de erro
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new java.util.LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return Map.of("errors", errors);
    }

    /**
     * Trata falhas de validação em parâmetros de método ({@code @RequestParam}, {@code @PathVariable}).
     *
     * @param ex exceção com os resultados de validação por parâmetro
     * @return mapa com lista de mensagens de erro
     */
    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHandlerMethodValidation(HandlerMethodValidationException ex) {
        java.util.List<String> errors = ex.getAllValidationResults().stream()
                .flatMap(r -> r.getResolvableErrors().stream())
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();
        return Map.of("errors", errors);
    }

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
