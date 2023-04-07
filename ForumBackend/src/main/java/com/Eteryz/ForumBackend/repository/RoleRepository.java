package com.Eteryz.ForumBackend.repository;

import com.Eteryz.ForumBackend.types.ERole;
import com.Eteryz.ForumBackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
