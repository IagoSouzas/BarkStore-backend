package com.senac.barksotre.controller;

import com.senac.barksotre.dto.endereco.EnderecoRequestDTO;
import com.senac.barksotre.dto.endereco.EnderecoResponseDTO;
import com.senac.barksotre.service.EnderecoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enderecos")
@RequiredArgsConstructor
public class EnderecoController {

	private final EnderecoService enderecoService;

	@PostMapping
	public ResponseEntity<EnderecoResponseDTO> criar(
			Authentication auth, @Valid @RequestBody EnderecoRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.criar(auth.getName(), dto));
	}

	@GetMapping
	public List<EnderecoResponseDTO> listar(Authentication auth) {
		return enderecoService.listar(auth.getName());
	}

	@PutMapping("/{id}")
	public EnderecoResponseDTO atualizar(
			Authentication auth, @PathVariable Long id, @Valid @RequestBody EnderecoRequestDTO dto) {
		return enderecoService.atualizar(auth.getName(), id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(Authentication auth, @PathVariable Long id) {
		enderecoService.excluir(auth.getName(), id);
		return ResponseEntity.noContent().build();
	}
}
