package com.nurseVarsity.BackEndCore.repository;

import com.nurseVarsity.BackEndCore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByAuthority (String authority);
}
