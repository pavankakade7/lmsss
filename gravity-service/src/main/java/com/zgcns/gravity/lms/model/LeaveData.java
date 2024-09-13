package com.zgcns.gravity.lms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LeaveData {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 	
	    private Double casualLeaves;
	    private Double medicalLeaves;
	    private Double privilegedLeaves;
	    private Double unpaidLeaves;
	    
	    @OneToOne
	    @JoinColumn(name = "userId")
	    @JsonBackReference
	    private User user;


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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LeaveData(Long id, Double casualLeaves, Double medicalLeaves, Double privilegedLeaves, Double unpaidLeaves, User user) {
		this.id = id;
		this.casualLeaves = casualLeaves;
		this.medicalLeaves = medicalLeaves;
		this.privilegedLeaves = privilegedLeaves;
		this.unpaidLeaves = unpaidLeaves;
		this.user = user;
	}

	public  LeaveData(){
		super();
	}

	@Override
	public String toString() {
		return "LeaveData{" +
				"id=" + id +
				", casualLeaves=" + casualLeaves +
				", medicalLeaves=" + medicalLeaves +
				", privilegedLeaves=" + privilegedLeaves +
				", unpaidLeaves=" + unpaidLeaves +
				", user=" + user +
				'}';
	}
}
