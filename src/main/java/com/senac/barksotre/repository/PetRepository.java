package com.senac.barksotre.repository;

import com.senac.barksotre.domain.entity.Pet;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long> {

	List<Pet> findAllByUsuarioId(Long usuarioId);
}
