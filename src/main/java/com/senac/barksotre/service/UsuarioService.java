package com.senac.barksotre.service;

import com.senac.barksotre.domain.entity.Usuario;
import com.senac.barksotre.dto.usuario.UsuarioCreateDTO;
import com.senac.barksotre.dto.usuario.UsuarioResponseDTO;
import com.senac.barksotre.dto.usuario.UsuarioUpdateDTO;
import com.senac.barksotre.exception.ResourceNotFoundException;
import com.senac.barksotre.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public UsuarioResponseDTO criar(UsuarioCreateDTO dto) {
		if (usuarioRepository.existsByEmail(dto.email())) {
			throw new IllegalArgumentException("E-mail já cadastrado");
		}
		Usuario usuario = new Usuario();
		usuario.setNomeCompleto(dto.nomeCompleto());
		usuario.setEmail(dto.email().toLowerCase());
		usuario.setSenha(passwordEncoder.encode(dto.senha()));
		return toResponse(usuarioRepository.save(usuario));
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorEmail(String email) {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
	}

	@Transactional(readOnly = true)
	public UsuarioResponseDTO buscarAtual(String email) {
		return toResponse(buscarPorEmail(email));
	}

	@Transactional
	public UsuarioResponseDTO atualizar(String emailAtual, UsuarioUpdateDTO dto) {
		Usuario usuario = buscarPorEmail(emailAtual);
		if (!emailAtual.equalsIgnoreCase(dto.email())
				&& usuarioRepository.existsByEmail(dto.email().toLowerCase())) {
			throw new IllegalArgumentException("E-mail já cadastrado");
		}
		usuario.setNomeCompleto(dto.nomeCompleto());
		usuario.setEmail(dto.email().toLowerCase());
		if (dto.senha() != null && !dto.senha().isBlank()) {
			usuario.setSenha(passwordEncoder.encode(dto.senha()));
		}
		return toResponse(usuarioRepository.save(usuario));
	}

	@Transactional
	public void excluir(String email) {
		Usuario usuario = buscarPorEmail(email);
		usuarioRepository.delete(usuario);
	}

	private UsuarioResponseDTO toResponse(Usuario usuario) {
		return new UsuarioResponseDTO(usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail());
	}
}
