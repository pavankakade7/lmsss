package com.zgcns.gravity.lms.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.lms.model.Employee;
import com.zgcns.gravity.lms.model.LeaveRequest;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByEmployee(Employee employee);


//    LeaveRequest findByLeaveId(@Param("leaveId")Long leaveId);
    
    Optional<LeaveRequest> findByLeaveId(@Param("leaveId") Long leaveId);
    List<LeaveRequest> findAllByEmployee_EmpId(Long empId);
//    Optional <LeaveRequest> findbyEmployeeId(Long empId);
    
	List<LeaveRequest> findAllLeaveRequestByUserId(Long userId);
}