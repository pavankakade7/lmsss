package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zgcns.gravity.lms.dto.MedicalCertificateDTO;
import com.zgcns.gravity.lms.model.MedicalCertificate;

@Component
public class MedicalCertificateDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public MedicalCertificateDTO convertMedicalCertificatetoMedicalCertificateDTO(MedicalCertificate medicalCertificate){
        MedicalCertificateDTO medicalCertificateDTO = modelMapper.map(medicalCertificate, MedicalCertificateDTO.class);
        return medicalCertificateDTO;

    }

    public MedicalCertificate convertMedicalCertificateDTOtoMedicalCertificate(MedicalCertificateDTO medicalCertificateDTO){
        MedicalCertificate medicalCertificate = modelMapper.map(medicalCertificateDTO, MedicalCertificate.class);
        return medicalCertificate;
    }

}
