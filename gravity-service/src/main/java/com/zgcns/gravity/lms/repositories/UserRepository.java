package com.zgcns.gravity.lms.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.lms.model.User;



@Repository
public interface UserRepository extends JpaRepository<User,Long> {
 Optional<User> findByEmail(String email);
 Optional<User> findByUsername(String username);
 Optional<User> getUserByEmail(String email);
 User findByFirstName(String firstName);
 
 
 
 @Override
	default long count() {
		// TODO Auto-generated method stub
		return 0;
	}
}