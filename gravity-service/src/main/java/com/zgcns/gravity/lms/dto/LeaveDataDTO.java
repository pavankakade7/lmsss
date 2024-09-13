package com.zgcns.gravity.lms.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveDataDTO {
		
		
		private Long id;
	    private Double casualLeaves;
	    private Double medicalLeaves;
	    private Double privilegedLeaves;
	    private Double unpaidLeaves;
	   
	    private UserDTO userDTO;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Double getCasualLeaves() {
			return casualLeaves;
		}
		public void setCasualLeaves(Double casualLeaves) {
			this.casualLeaves = casualLeaves;
		}
		public Double getMedicalLeaves() {
			return medicalLeaves;
		}
		public void setMedicalLeaves(Double medicalLeaves) {
			this.medicalLeaves = medicalLeaves;
		}
		public Double getPrivilegedLeaves() {
			return privilegedLeaves;
		}
		public void setPrivilegedLeaves(Double privilegedLeaves) {
			this.privilegedLeaves = privilegedLeaves;
		}
		public Double getUnpaidLeaves() {
			return unpaidLeaves;
		}
		public void setUnpaidLeaves(Double unpaidLeaves) {
			this.unpaidLeaves = unpaidLeaves;
		}
		public UserDTO getUserDTO() {
			return userDTO;
		}
		public void setUserDTO(UserDTO userDTO) {
			this.userDTO = userDTO;
		}
	
	    
	    
}
