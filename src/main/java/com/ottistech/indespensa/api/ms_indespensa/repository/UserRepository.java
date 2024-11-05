package com.ottistech.indespensa.api.ms_indespensa.repository;

import com.ottistech.indespensa.api.ms_indespensa.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Transactional
    @Procedure(procedureName = "public.atualizar_status_premium")
    void switchUserPlan(
            @Param("p_user_id")
            Integer userId
    );

    @Transactional
    @Procedure(procedureName = "public.desativar_conta_usuario")
    void deactivateUser(
            @Param("p_user_id")
            Integer userId
    );
}
