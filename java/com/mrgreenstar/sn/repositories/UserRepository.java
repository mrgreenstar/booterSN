package com.mrgreenstar.sn.repositories;

import com.mrgreenstar.sn.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
    User findUserById(Long id);
}
