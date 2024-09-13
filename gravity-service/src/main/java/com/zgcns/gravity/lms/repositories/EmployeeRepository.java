package com.zgcns.gravity.lms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.lms.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
}