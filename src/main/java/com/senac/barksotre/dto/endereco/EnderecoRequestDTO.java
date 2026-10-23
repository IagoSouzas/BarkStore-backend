package com.senac.barksotre.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EnderecoRequestDTO(
		@NotBlank @Size(max = 80) String nomeEndereco,
		@NotBlank @Size(max = 9) String cep,
		@NotBlank @Size(max = 150) String rua,
		@NotBlank @Size(max = 20) String numero,
		@Size(max = 100) String complemento,
		@NotBlank @Size(max = 80) String bairro,
		@NotBlank @Size(max = 80) String cidade,
		@NotBlank @Size(min = 2, max = 2) String estado) {
}
