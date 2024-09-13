package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zgcns.gravity.lms.dto.LeaveDataDTO;
import com.zgcns.gravity.lms.model.LeaveData;

@Component
public class LeaveDataDTOConverter {
	
	
	@Autowired
	private ModelMapper modelMapper;
	
	public LeaveDataDTO convertLeaveDatatoLeaveDataDTO(LeaveData leaveData) {
		LeaveDataDTO leaveDataDTO = modelMapper.map(leaveData, LeaveDataDTO.class);
		return leaveDataDTO;
	}
	
	public LeaveData convertLeaveDataDTOtoLeaveData(LeaveDataDTO leaveDataDTO) {
		LeaveData leaveData = modelMapper.map(leaveDataDTO, LeaveData.class);
		return leaveData;
	}
}
