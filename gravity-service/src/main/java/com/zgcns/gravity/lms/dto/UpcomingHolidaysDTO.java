package com.zgcns.gravity.lms.dto;

import java.time.LocalDate;

import lombok.Data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpcomingHolidaysDTO {
	
	
    private Long id;
    private String title;
    private String day;
    private LocalDate holidayDate;
    private boolean optional;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public LocalDate getHolidayDate() {
		return holidayDate;
	}
	public void setHolidayDate(LocalDate holidayDate) {
		this.holidayDate = holidayDate;
	}
	public boolean isOptional() {
		return optional;
	}
	public void setOptional(boolean optional) {
		this.optional = optional;
	}
	
    
    
    
}
