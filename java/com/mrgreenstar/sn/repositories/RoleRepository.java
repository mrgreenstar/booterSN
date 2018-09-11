package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(String name);
}
