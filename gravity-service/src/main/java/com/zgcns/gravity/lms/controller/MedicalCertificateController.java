package com.zgcns.gravity.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.zgcns.gravity.lms.dto.MedicalCertificateDTO;
import com.zgcns.gravity.lms.services.MedicalCertificateService;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medical-certificate")
public class MedicalCertificateController {

    private final MedicalCertificateService medicalCertificateService;

    @Autowired
    public MedicalCertificateController(MedicalCertificateService medicalCertificateService) {
        this.medicalCertificateService = medicalCertificateService;
    }

    @GetMapping
    public List<MedicalCertificateDTO> getAllMedicalCertificates() {
        return medicalCertificateService.getAllMedicalCertificates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalCertificateDTO> getMedicalCertificateById(@PathVariable("id") Long id) throws Exception {
        Optional<MedicalCertificateDTO> medicalCertificateDTO = medicalCertificateService.getMedicalCertificateById(id);
        return medicalCertificateDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadMedicalCertificate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("leaveId") Long leaveId,
            @RequestParam("userId") Long userId) {
        try {
            MedicalCertificateDTO medicalCertificateDTO = medicalCertificateService.uploadMedicalCertificate(file, leaveId, userId);
            return new ResponseEntity<>("File uploaded successfully. Filename: " + medicalCertificateDTO.getFileName(), HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            return new ResponseEntity<>("Error uploading file", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFileById(@PathVariable("id") Long id) throws Exception {
        return medicalCertificateService.downloadMedicalCertificate(id);
    }

    @GetMapping("/download/leave/{leaveId}")
    public ResponseEntity<Resource> downloadFileByLeaveId(@PathVariable("leaveId") Long leaveId) throws Exception {
        Optional<MedicalCertificateDTO> optionalMedicalCertificateDTO = medicalCertificateService.getMedicalCertificateByLeaveId(leaveId);

        if (optionalMedicalCertificateDTO.isPresent()) {
            MedicalCertificateDTO medicalCertificateDTO = optionalMedicalCertificateDTO.get();

            // Determine the MIME type based on the file extension
            String mimeType = Files.probeContentType(Paths.get(medicalCertificateDTO.getFileType()));
            if (mimeType == null) {
                mimeType = "application/octet-stream"; // Default MIME type
            } else if (!mimeType.equals("image/jpeg") && !mimeType.equals("application/pdf")) {
                throw new Exception("File should be JPEG or PDF");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + medicalCertificateDTO.getFileName() + "\"")
                    .body(new ByteArrayResource(medicalCertificateDTO.getData()));
        } else {
            throw new Exception("Medical certificate not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicalCertificateById(@PathVariable("id") Long id) {
        try {
            if (medicalCertificateService.getMedicalCertificateById(id).isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            medicalCertificateService.deleteMedicalCertificateById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
