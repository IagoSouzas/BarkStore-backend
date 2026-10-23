package com.senac.barksotre.controller;

import com.senac.barksotre.dto.pet.PetRequestDTO;
import com.senac.barksotre.dto.pet.PetResponseDTO;
import com.senac.barksotre.service.PetService;
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
@RequestMapping("/pets")
@RequiredArgsConstructor
public class PetController {

	private final PetService petService;

	@PostMapping
	public ResponseEntity<PetResponseDTO> criar(Authentication auth, @Valid @RequestBody PetRequestDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(petService.criar(auth.getName(), dto));
	}

	@GetMapping
	public List<PetResponseDTO> listar(Authentication auth) {
		return petService.listar(auth.getName());
	}

	@PutMapping("/{id}")
	public PetResponseDTO atualizar(
			Authentication auth, @PathVariable Long id, @Valid @RequestBody PetRequestDTO dto) {
		return petService.atualizar(auth.getName(), id, dto);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> excluir(Authentication auth, @PathVariable Long id) {
		petService.excluir(auth.getName(), id);
		return ResponseEntity.noContent().build();
	}
}
