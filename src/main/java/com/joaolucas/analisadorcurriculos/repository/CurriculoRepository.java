package com.joaolucas.analisadorcurriculos.repository;

import com.joaolucas.analisadorcurriculos.entity.CurriculoAnalisado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurriculoRepository extends JpaRepository<CurriculoAnalisado, Long> {
}
