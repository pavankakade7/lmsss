package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zgcns.gravity.lms.dto.LeaveRequestDTO;
import com.zgcns.gravity.lms.model.LeaveRequest;

@Component
public class LeaveRequestDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public LeaveRequestDTO convertLeaveRequesttoLeaveRequestDTO(LeaveRequest leaveRequest) {
        
        return modelMapper.map(leaveRequest, LeaveRequestDTO.class);
    }

    public LeaveRequest convertLeaveRequestDTOtoLeaveRequest(LeaveRequestDTO leaveRequestDTO) {
        return modelMapper.map(leaveRequestDTO, LeaveRequest.class);
    }
}
