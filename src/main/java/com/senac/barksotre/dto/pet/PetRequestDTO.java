package com.senac.barksotre.dto.pet;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public record PetRequestDTO(
		@NotBlank @Size(max = 80) String nome,
		@NotBlank @Size(max = 40) String tipo,
		LocalDate dataNascimento,
		@DecimalMin("0.01") BigDecimal peso,
		@Size(max = 500) String fotoUrl) {
}
