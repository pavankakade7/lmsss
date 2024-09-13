package com.zgcns.gravity.authmanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.authmanager.entity.GravityUser;

import java.util.Optional;
@Repository
public interface AuthUserRepository extends JpaRepository<GravityUser, Long> {
    Optional<GravityUser> findByUsername(String username);
}