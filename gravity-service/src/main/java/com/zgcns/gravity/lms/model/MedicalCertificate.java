package com.zgcns.gravity.lms.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;


@Entity
@Data
@Table(name = "medicalCertificate")
@Builder
public class MedicalCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long leaveId;
    private Long userId;
    private String fileName;
    private String fileType;
    @Lob
    private byte[] data;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(Long leaveId) {
        this.leaveId = leaveId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public MedicalCertificate (){
        super();
    }

    public MedicalCertificate(Long id, Long leaveId, Long userId, String fileName, String fileType, byte[] data) {
        this.id = id;
        this.leaveId = leaveId;
        this.userId = userId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    @Override
    public String toString() {
        return "MedicalCertificate{" +
                "id=" + id +
                ", leaveId=" + leaveId +
                ", userId=" + userId +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }


}
