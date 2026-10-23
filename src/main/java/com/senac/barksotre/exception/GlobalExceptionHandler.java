package com.senac.barksotre.exception;

import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleMethodArgumentNotValid(
			MethodArgumentNotValidException exception,
			HttpServletRequest request) {
		Map<String, String> fields = new LinkedHashMap<>();
		for (FieldError error : exception.getBindingResult().getFieldErrors()) {
			fields.putIfAbsent(error.getField(), error.getDefaultMessage());
		}
		return build(HttpStatus.BAD_REQUEST, "Dados inválidos", request, fields);
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	public ResponseEntity<ApiError> handleMethodValidation(
			HandlerMethodValidationException exception,
			HttpServletRequest request) {
		return build(HttpStatus.BAD_REQUEST, "Dados inválidos", request, Map.of());
	}

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<ApiError> handleBadRequest(
			IllegalArgumentException exception,
			HttpServletRequest request) {
		return build(HttpStatus.BAD_REQUEST, exception.getMessage(), request, Map.of());
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(
			ResourceNotFoundException exception,
			HttpServletRequest request) {
		return build(HttpStatus.NOT_FOUND, exception.getMessage(), request, Map.of());
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiError> handleBadCredentials(
			BadCredentialsException exception,
			HttpServletRequest request) {
		return build(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos", request, Map.of());
	}

	private ResponseEntity<ApiError> build(
			HttpStatus status,
			String message,
			HttpServletRequest request,
			Map<String, String> fields) {
		ApiError body = new ApiError(
				Instant.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				request.getRequestURI(),
				fields);
		return ResponseEntity.status(status).body(body);
	}
}
