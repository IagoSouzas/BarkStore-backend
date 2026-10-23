package com.senac.barksotre.service;

import com.senac.barksotre.domain.entity.Pet;
import com.senac.barksotre.dto.pet.PetRequestDTO;
import com.senac.barksotre.dto.pet.PetResponseDTO;
import com.senac.barksotre.exception.ResourceNotFoundException;
import com.senac.barksotre.repository.PetRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PetService {

	private final PetRepository petRepository;
	private final UsuarioService usuarioService;

	@Transactional
	public PetResponseDTO criar(String email, PetRequestDTO dto) {
		Pet pet = new Pet();
		pet.setUsuario(usuarioService.buscarPorEmail(email));
		copy(dto, pet);
		return toResponse(petRepository.save(pet));
	}

	@Transactional(readOnly = true)
	public List<PetResponseDTO> listar(String email) {
		Long usuarioId = usuarioService.buscarPorEmail(email).getId();
		return petRepository.findAllByUsuarioId(usuarioId).stream().map(this::toResponse).toList();
	}

	@Transactional
	public PetResponseDTO atualizar(String email, Long id, PetRequestDTO dto) {
		Pet pet = findOwned(email, id);
		copy(dto, pet);
		return toResponse(petRepository.save(pet));
	}

	@Transactional
	public void excluir(String email, Long id) {
		petRepository.delete(findOwned(email, id));
	}

	private Pet findOwned(String email, Long id) {
		Long usuarioId = usuarioService.buscarPorEmail(email).getId();
		return petRepository.findById(id)
				.filter(pet -> pet.getUsuario().getId().equals(usuarioId))
				.orElseThrow(() -> new ResourceNotFoundException("Pet não encontrado"));
	}

	private void copy(PetRequestDTO dto, Pet pet) {
		pet.setNome(dto.nome());
		pet.setTipo(dto.tipo());
		pet.setDataNascimento(dto.dataNascimento());
		pet.setPeso(dto.peso());
		pet.setFotoUrl(dto.fotoUrl());
	}

	private PetResponseDTO toResponse(Pet pet) {
		return new PetResponseDTO(pet.getId(), pet.getNome(), pet.getTipo(),
				pet.getDataNascimento(), pet.getPeso(), pet.getFotoUrl());
	}
}
