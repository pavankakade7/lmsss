package com.zgcns.gravity.lms.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@Entity
@Table(name = "leaveRequest")
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String leaveType;
    private String reason;

    @Enumerated(EnumType.STRING)
    private RequestStatus status = RequestStatus.PENDING;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id")
    private Employee employee;

	private Long userId;

	private Double days;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Double getDays() {
		return days;
	}

	public void setDays(Double days) {
		this.days = days;
	}

	public LeaveRequest(Long leaveId, LocalDate startDate, LocalDate endDate, String leaveType, String reason,
			RequestStatus status, Employee employee, Double days, Long userId) {
		super();
		this.leaveId = leaveId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.leaveType = leaveType;
		this.reason = reason;
		this.status = status;
		this.employee = employee;
		this.days = days;
		this.userId = userId;
	}

	public LeaveRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "LeaveRequest [leaveId=" + leaveId + ", startDate=" + startDate + ", endDate=" + endDate + ", leaveType="
				+ leaveType + ", reason=" + reason + ", status=" + status + ", employee=" + employee + ", days=" + days +
				 ", userId=" + userId + "]";
	}








}
