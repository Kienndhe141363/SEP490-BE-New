package com.g96.ftms.repository;

import com.g96.ftms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query("SELECT r.roleName FROM Role r")
    List<String> findAllRoleNames();
    Optional<Role> findByRoleName(String roleName);
    List<Role> findAll();
}
