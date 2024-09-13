package com.zgcns.gravity.lms.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.lms.model.MedicalCertificate;

import java.util.Optional;

@Repository
public interface MedicalCertificateRepository extends JpaRepository<MedicalCertificate, Long> {
    Optional<MedicalCertificate> findByFileName(String fileName);
    Optional<MedicalCertificate> findByLeaveId(Long leaveId);
}