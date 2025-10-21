package com.bank.authentication_service.repo;

import com.bank.authentication_service.model.AuthDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthDetails,Long> {
    AuthDetails findByUsername(String username);
}
