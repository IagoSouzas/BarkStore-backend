package com.senac.barksotre.dto.auth;

public record TokenResponseDTO(String token, String type, long expiresIn) {
}
