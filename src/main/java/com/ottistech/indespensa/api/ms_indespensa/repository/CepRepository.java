package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse;
import com.ottistech.indespensa.api.ms_indespensa.model.Cep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CepRepository extends JpaRepository<Cep, String> {

    @Query("""
            SELECT new com.ottistech.indespensa.api.ms_indespensa.dto.cep.response.CepApiResponse(
                c.cepId,
                c.street,
                c.state,
                c.city
            ) FROM Cep c
            WHERE c.cepId = :cep
            """)
    Optional<CepApiResponse> findCep(String cep);
}
