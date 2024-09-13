package com.zgcns.gravity.lms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.zgcns.gravity.lms.converter.MedicalCertificateDTOConverter;
import com.zgcns.gravity.lms.dto.MedicalCertificateDTO;
import com.zgcns.gravity.lms.model.MedicalCertificate;
import com.zgcns.gravity.lms.repositories.MedicalCertificateRepository;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicalCertificateService {

    private final MedicalCertificateRepository medicalCertificateRepository;
    private final MedicalCertificateDTOConverter medicalCertificateDTOConverter;

    @Autowired
    public MedicalCertificateService(MedicalCertificateRepository medicalCertificateRepository, 
                                     MedicalCertificateDTOConverter medicalCertificateDTOConverter) {
        this.medicalCertificateRepository = medicalCertificateRepository;
        this.medicalCertificateDTOConverter = medicalCertificateDTOConverter;
    }

    public List<MedicalCertificateDTO> getAllMedicalCertificates() {
        return medicalCertificateRepository.findAll()
                                           .stream()
                                           .map(medicalCertificateDTOConverter::convertMedicalCertificatetoMedicalCertificateDTO)
                                           .collect(Collectors.toList());
    }

    public Optional<MedicalCertificateDTO> getMedicalCertificateById(Long id) throws Exception {
        MedicalCertificate medicalCertificate = medicalCertificateRepository.findById(id)
                                                                           .orElseThrow(() -> new Exception("File not found with Id: " + id));
        return Optional.of(medicalCertificateDTOConverter.convertMedicalCertificatetoMedicalCertificateDTO(medicalCertificate));
    }

    @Transactional
    public Optional<MedicalCertificateDTO> getMedicalCertificateByLeaveId(Long leaveId) throws Exception {
        MedicalCertificate medicalCertificate = medicalCertificateRepository.findByLeaveId(leaveId)
                                                                           .orElseThrow(() -> new Exception("File not found with Leave Id: " + leaveId));
        return Optional.of(medicalCertificateDTOConverter.convertMedicalCertificatetoMedicalCertificateDTO(medicalCertificate));
    }

    public MedicalCertificateDTO uploadMedicalCertificate(MultipartFile file, Long leaveId, Long userId) throws Exception {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (fileName.contains("..")) {
                throw new Exception("Filename contains invalid path sequence");
            }
            MedicalCertificate medicalCertificate = new MedicalCertificate();
            medicalCertificate.setData(file.getBytes());
            medicalCertificate.setFileType(file.getContentType());
            medicalCertificate.setFileName(fileName);
            medicalCertificate.setUserId(userId);
            medicalCertificate.setLeaveId(leaveId);

            MedicalCertificate savedCertificate = medicalCertificateRepository.save(medicalCertificate);
            return medicalCertificateDTOConverter.convertMedicalCertificatetoMedicalCertificateDTO(savedCertificate);
        } catch (Exception e) {
            throw new Exception("Could not save file: " + fileName, e);
        }
    }

    public ResponseEntity<Resource> downloadMedicalCertificate(Long id) throws Exception {
        MedicalCertificate medicalCertificate = medicalCertificateRepository.findById(id)
                                                                            .orElseThrow(() -> new Exception("Medical certificate not found"));
        String contentType = medicalCertificate.getFileType();
        if (contentType == null || !contentType.contains("/")) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        return ResponseEntity.ok()
                             .contentType(MediaType.parseMediaType(contentType))
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + medicalCertificate.getFileName() + "\"")
                             .body(new ByteArrayResource(medicalCertificate.getData()));
    }

    public void deleteMedicalCertificateById(Long id) {
        medicalCertificateRepository.deleteById(id);
    }
}
