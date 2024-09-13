package com.zgcns.gravity.lms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.lms.dto.LeaveDataDTO;
import com.zgcns.gravity.lms.dto.LeaveRequestDTO;
import com.zgcns.gravity.lms.model.LeaveData;
import com.zgcns.gravity.lms.model.LeaveRequest;
import com.zgcns.gravity.lms.repositories.LeaveDataRepository;
import com.zgcns.gravity.lms.converter.LeaveDataDTOConverter;

@Service
public class LeaveDataService {

    @Autowired
    private LeaveDataRepository leaveDataRepository;

    @Autowired
    private LeaveDataDTOConverter leaveDataDTOConverter;

    public LeaveDataDTO getLeaveDataByUserId(Long userId) {
        LeaveData leaveData = leaveDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Leave data not found for employeeId: " + userId));
        return leaveDataDTOConverter.convertLeaveDatatoLeaveDataDTO(leaveData);
    }

    public LeaveDataDTO saveLeaveData(LeaveDataDTO leaveDataDTO) {
        LeaveData leaveData = leaveDataDTOConverter.convertLeaveDataDTOtoLeaveData(leaveDataDTO);
        LeaveData savedLeaveData = leaveDataRepository.save(leaveData);
        return leaveDataDTOConverter.convertLeaveDatatoLeaveDataDTO(savedLeaveData);
    }

    public LeaveDataDTO updateLeaveData(Long userId, LeaveDataDTO updatedLeaveDataDTO) {
        // Retrieve existing leave data for the user
        LeaveData existingLeaveData = leaveDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Leave data not found for employeeId: " + userId));

        // Update only the leave types that are specified in the updatedLeaveDataDTO
        if (updatedLeaveDataDTO.getCasualLeaves() != null) {
            existingLeaveData.setCasualLeaves(updatedLeaveDataDTO.getCasualLeaves());
        }
        if (updatedLeaveDataDTO.getMedicalLeaves() != null) {
            existingLeaveData.setMedicalLeaves(updatedLeaveDataDTO.getMedicalLeaves());
        }
        if (updatedLeaveDataDTO.getPrivilegedLeaves() != null) {
            existingLeaveData.setPrivilegedLeaves(updatedLeaveDataDTO.getPrivilegedLeaves());
        }
        if (updatedLeaveDataDTO.getUnpaidLeaves() != null) {
            existingLeaveData.setUnpaidLeaves(updatedLeaveDataDTO.getUnpaidLeaves());
        }

        // Save the updated leave data to the repository
        LeaveData updatedLeaveData = leaveDataRepository.save(existingLeaveData);
        return leaveDataDTOConverter.convertLeaveDatatoLeaveDataDTO(updatedLeaveData);
    }

    public void updateLeaveDataApprove(Long userId, LeaveRequestDTO updatedRequest) {
        // Retrieve existing leave data for the user
        LeaveData existingLeaveData = leaveDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Leave data not found for employeeId: " + userId));

        // Subtract the number of days from the appropriate leave type
        switch (updatedRequest.getLeaveType()) {
            case "casualLeaves":
                if (existingLeaveData.getCasualLeaves() >= updatedRequest.getDays()) {
                    existingLeaveData.setCasualLeaves(existingLeaveData.getCasualLeaves() - updatedRequest.getDays());
                } else {
                    throw new IllegalArgumentException("Insufficient casual leave balance");
                }
                break;
            case "medicalLeaves":
                if (existingLeaveData.getMedicalLeaves() >= updatedRequest.getDays()) {
                    existingLeaveData.setMedicalLeaves(existingLeaveData.getMedicalLeaves() - updatedRequest.getDays());
                } else {
                    throw new IllegalArgumentException("Insufficient medical leave balance");
                }
                break;
            case "privilegedLeaves":
                if (existingLeaveData.getPrivilegedLeaves() >= updatedRequest.getDays()) {
                    existingLeaveData.setPrivilegedLeaves(existingLeaveData.getPrivilegedLeaves() - updatedRequest.getDays());
                } else {
                    throw new IllegalArgumentException("Insufficient privileged leave balance");
                }
                break;
            case "unpaidLeaves":
                if (existingLeaveData.getUnpaidLeaves() >= updatedRequest.getDays()) {
                    existingLeaveData.setUnpaidLeaves(existingLeaveData.getUnpaidLeaves() - updatedRequest.getDays());
                } else {
                    throw new IllegalArgumentException("Insufficient unpaid leave balance");
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown leave type");
        }

        // Save the updated leave data to the repository
        leaveDataRepository.save(existingLeaveData);
    }

    public void deleteLeaveData(Long userId) {
        LeaveData existingLeaveData = leaveDataRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Leave data not found for employeeId: " + userId));
        leaveDataRepository.delete(existingLeaveData);
    }
}
