package com.zgcns.gravity.lms.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zgcns.gravity.lms.model.RequestStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveRequestDTO {
		
	
	 	
		private Long leaveId;
	    private LocalDate startDate;
	    private LocalDate endDate;
	    private String leaveType;
	    private String reason;
	    private RequestStatus status = RequestStatus.PENDING;
	
	    private EmployeeDTO employeeDTO;
		private Long userId;
		private Double days;
		
		public Long getLeaveId() {
			return leaveId;
		}
		public void setLeaveId(Long leaveId) {
			this.leaveId = leaveId;
		}
		public LocalDate getStartDate() {
			return startDate;
		}
		public void setStartDate(LocalDate startDate) {
			this.startDate = startDate;
		}
		public LocalDate getEndDate() {
			return endDate;
		}
		public void setEndDate(LocalDate endDate) {
			this.endDate = endDate;
		}
		public String getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(String leaveType) {
			this.leaveType = leaveType;
		}
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public RequestStatus getStatus() {
			return status;
		}
		public void setStatus(RequestStatus status) {
			this.status = status;
		}
	
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public Double getDays() {
			return days;
		}
		public void setDays(Double days) {
			this.days = days;
		}
		public EmployeeDTO getEmployeeDTO() {
			return employeeDTO;
		}
		public void setEmployeeDTO(EmployeeDTO employeeDTO) {
			this.employeeDTO = employeeDTO;
		}
		
		
		

}
