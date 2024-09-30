package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CepRepository extends JpaRepository<Cep, String> {
}
