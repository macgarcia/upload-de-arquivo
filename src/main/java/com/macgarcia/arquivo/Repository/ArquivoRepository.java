package com.macgarcia.arquivo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macgarcia.arquivo.model.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long> {

}
