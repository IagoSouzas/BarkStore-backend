package com.senac.barksotre.dto.pet;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PetResponseDTO(
		Long id, String nome, String tipo, LocalDate dataNascimento, BigDecimal peso, String fotoUrl) {
}
