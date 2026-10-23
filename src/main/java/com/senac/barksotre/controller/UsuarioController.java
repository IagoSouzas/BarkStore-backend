package com.senac.barksotre.controller;

import com.senac.barksotre.dto.usuario.UsuarioCreateDTO;
import com.senac.barksotre.dto.usuario.UsuarioResponseDTO;
import com.senac.barksotre.dto.usuario.UsuarioUpdateDTO;
import com.senac.barksotre.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

	private final UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<UsuarioResponseDTO> criar(@Valid @RequestBody UsuarioCreateDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.criar(dto));
	}

	@GetMapping("/me")
	public UsuarioResponseDTO buscar(Authentication authentication) {
		return usuarioService.buscarAtual(authentication.getName());
	}

	@PutMapping("/me")
	public UsuarioResponseDTO atualizar(
			Authentication authentication, @Valid @RequestBody UsuarioUpdateDTO dto) {
		return usuarioService.atualizar(authentication.getName(), dto);
	}

	@DeleteMapping("/me")
	public ResponseEntity<Void> excluir(Authentication authentication) {
		usuarioService.excluir(authentication.getName());
		return ResponseEntity.noContent().build();
	}
}
