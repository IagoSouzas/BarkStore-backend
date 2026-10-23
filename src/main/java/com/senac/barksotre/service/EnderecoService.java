package com.senac.barksotre.service;

import com.senac.barksotre.domain.entity.Endereco;
import com.senac.barksotre.dto.endereco.EnderecoRequestDTO;
import com.senac.barksotre.dto.endereco.EnderecoResponseDTO;
import com.senac.barksotre.exception.ResourceNotFoundException;
import com.senac.barksotre.repository.EnderecoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EnderecoService {

	private final EnderecoRepository enderecoRepository;
	private final UsuarioService usuarioService;

	@Transactional
	public EnderecoResponseDTO criar(String email, EnderecoRequestDTO dto) {
		Endereco endereco = new Endereco();
		endereco.setUsuario(usuarioService.buscarPorEmail(email));
		copy(dto, endereco);
		return toResponse(enderecoRepository.save(endereco));
	}

	@Transactional(readOnly = true)
	public List<EnderecoResponseDTO> listar(String email) {
		Long usuarioId = usuarioService.buscarPorEmail(email).getId();
		return enderecoRepository.findAllByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
	}

	@Transactional
	public EnderecoResponseDTO atualizar(String email, Long id, EnderecoRequestDTO dto) {
		Endereco endereco = findOwned(email, id);
		copy(dto, endereco);
		return toResponse(enderecoRepository.save(endereco));
	}

	@Transactional
	public void excluir(String email, Long id) {
		enderecoRepository.delete(findOwned(email, id));
	}

	private Endereco findOwned(String email, Long id) {
		Long usuarioId = usuarioService.buscarPorEmail(email).getId();
		return enderecoRepository.findById(id)
				.filter(endereco -> endereco.getUsuario().getId().equals(usuarioId))
				.orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado"));
	}

	private void copy(EnderecoRequestDTO dto, Endereco endereco) {
		endereco.setNomeEndereco(dto.nomeEndereco());
		endereco.setCep(dto.cep());
		endereco.setRua(dto.rua());
		endereco.setNumero(dto.numero());
		endereco.setComplemento(dto.complemento());
		endereco.setBairro(dto.bairro());
		endereco.setCidade(dto.cidade());
		endereco.setEstado(dto.estado().toUpperCase());
	}

	private EnderecoResponseDTO toResponse(Endereco endereco) {
		return new EnderecoResponseDTO(endereco.getId(), endereco.getNomeEndereco(), endereco.getCep(),
				endereco.getRua(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(),
				endereco.getCidade(), endereco.getEstado());
	}
}
