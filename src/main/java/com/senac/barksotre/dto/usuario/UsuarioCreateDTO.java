package com.senac.barksotre.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioCreateDTO(
		@NotBlank @Size(max = 120) String nomeCompleto,
		@NotBlank @Email @Size(max = 180) String email,
		@NotBlank @Size(min = 6, max = 72) String senha) {
}
