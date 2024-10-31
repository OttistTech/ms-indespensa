package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Procedure(value = "adicionar_novo_usuario_e_endereco")
    void addNewUserAndAddress(
            @Param("p_email") String email,
            @Param("p_senha") String password,
            @Param("p_tipo_usuario") String type,
            @Param("p_nome") String name,
            @Param("p_data_nascimento") Date birthDate,
            @Param("p_tipo_empresa") String enterpriseType,
            @Param("p_is_premium") Boolean isPremium,
            @Param("p_cep_id") String cep,
            @Param("p_numero_endereco") Integer integer,
            @Param("p_cidade") String city,
            @Param("p_rua") String street,
            @Param("p_estado") String state
    );
}
