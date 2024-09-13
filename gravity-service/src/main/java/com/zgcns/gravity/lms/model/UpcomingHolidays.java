package com.zgcns.gravity.lms.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name = "upcoming_holidays")
public class UpcomingHolidays {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public UpcomingHolidays(Long id, String title, String day, LocalDate holidayDate, boolean optional) {
        this.id = id;
        this.title = title;
        this.day = day;
        this.holidayDate = holidayDate;
        this.optional = optional;
    }

    public UpcomingHolidays(){
        super();
    }

    @Override
    public String toString() {
        return "UpcomingHolidays{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", day='" + day + '\'' +
                ", holidayDate=" + holidayDate +
                ", optional=" + optional +
                '}';
    }
}
