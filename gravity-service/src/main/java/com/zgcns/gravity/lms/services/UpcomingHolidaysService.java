package com.zgcns.gravity.lms.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.lms.dto.UpcomingHolidaysDTO;
import com.zgcns.gravity.lms.converter.UpcomingHolidaysDTOConverter;
import com.zgcns.gravity.lms.model.UpcomingHolidays;
import com.zgcns.gravity.lms.repositories.UpcomingHolidaysRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UpcomingHolidaysService {

    private final UpcomingHolidaysRepository upcomingHolidaysRepository;
    private final UpcomingHolidaysDTOConverter upcomingHolidaysDTOConverter;

    @Autowired
    public UpcomingHolidaysService(UpcomingHolidaysRepository upcomingHolidaysRepository, UpcomingHolidaysDTOConverter upcomingHolidaysDTOConverter) {
        this.upcomingHolidaysRepository = upcomingHolidaysRepository;
        this.upcomingHolidaysDTOConverter = upcomingHolidaysDTOConverter;
    }

    public List<UpcomingHolidaysDTO> getAllUpcomingHolidays() {
        List<UpcomingHolidays> holidays = upcomingHolidaysRepository.findAll();
        return holidays.stream()
                .map(upcomingHolidaysDTOConverter::convertUpcomingHolidaystoUpcomingHolidaysDTO)
                .collect(Collectors.toList());
    }

    public Optional<UpcomingHolidaysDTO> getUpcomingHolidaysById(Long id) {
        Optional<UpcomingHolidays> holiday = upcomingHolidaysRepository.findById(id);
        return holiday.map(upcomingHolidaysDTOConverter::convertUpcomingHolidaystoUpcomingHolidaysDTO);
    }

    public UpcomingHolidaysDTO saveUpcomingHolidays(UpcomingHolidaysDTO upcomingHolidaysDTO) {
        UpcomingHolidays holiday = upcomingHolidaysDTOConverter.convertUpcomingHolidyasDTOtoUpcomingHolidays(upcomingHolidaysDTO);
        UpcomingHolidays savedHoliday = upcomingHolidaysRepository.save(holiday);
        return upcomingHolidaysDTOConverter.convertUpcomingHolidaystoUpcomingHolidaysDTO(savedHoliday);
    }

    public UpcomingHolidaysDTO updateUpcomingHolidays(Long id, UpcomingHolidaysDTO upcomingHolidaysDTO) {
        UpcomingHolidays existingHoliday = upcomingHolidaysRepository.findById(id).orElseThrow(() -> new RuntimeException("Holiday not found"));
        existingHoliday.setHolidayDate(upcomingHolidaysDTO.getHolidayDate());
        existingHoliday.setDay(upcomingHolidaysDTO.getDay());
        existingHoliday.setTitle(upcomingHolidaysDTO.getTitle());
        existingHoliday.setOptional(upcomingHolidaysDTO.isOptional());

        UpcomingHolidays updatedHoliday = upcomingHolidaysRepository.save(existingHoliday);
        return upcomingHolidaysDTOConverter.convertUpcomingHolidaystoUpcomingHolidaysDTO(updatedHoliday);
    }

    public void deleteUpcomingHolidayById(Long id) {
        upcomingHolidaysRepository.deleteById(id);
    }

	
}
