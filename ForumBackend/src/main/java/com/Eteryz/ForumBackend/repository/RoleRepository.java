package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.models.ERole;
import com.Eteryz.ForumBackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
