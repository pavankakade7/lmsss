package com.zgcns.gravity.lms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zgcns.gravity.lms.model.UpcomingHolidays;

@Repository
public interface UpcomingHolidaysRepository extends JpaRepository<UpcomingHolidays, Long> {

}
