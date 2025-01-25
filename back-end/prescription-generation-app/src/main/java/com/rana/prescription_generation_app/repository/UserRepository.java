package com.rana.prescription_generation_app.repository;

import com.rana.prescription_generation_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

}
