package com.senac.barksotre.dto.endereco;

public record EnderecoResponseDTO(
		Long id, String nomeEndereco, String cep, String rua, String numero,
		String complemento, String bairro, String cidade, String estado) {
}
