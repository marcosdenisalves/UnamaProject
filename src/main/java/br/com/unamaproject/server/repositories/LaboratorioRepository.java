package br.com.unamaproject.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.unamaproject.server.domain.Laboratorio;

@Repository
public interface LaboratorioRepository extends JpaRepository<Laboratorio, Integer> {
	
}
