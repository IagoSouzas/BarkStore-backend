package com.senac.barksotre.repository;

import com.senac.barksotre.domain.entity.Endereco;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

	List<Endereco> findAllByUsuarioId(Long usuarioId);
}
