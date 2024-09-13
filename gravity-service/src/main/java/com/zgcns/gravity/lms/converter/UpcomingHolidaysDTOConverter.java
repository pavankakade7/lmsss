package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zgcns.gravity.lms.dto.UpcomingHolidaysDTO;
import com.zgcns.gravity.lms.model.UpcomingHolidays;

@Component
public class UpcomingHolidaysDTOConverter {

    @Autowired
    private ModelMapper modelMapper;

    public UpcomingHolidaysDTO convertUpcomingHolidaystoUpcomingHolidaysDTO(UpcomingHolidays upcomingHolidays){
        UpcomingHolidaysDTO upcomingHolidaysDTO = modelMapper.map(upcomingHolidays, UpcomingHolidaysDTO.class);
        return upcomingHolidaysDTO;
    }

    public UpcomingHolidays convertUpcomingHolidyasDTOtoUpcomingHolidays(UpcomingHolidaysDTO upcomingHolidaysDTO){
        UpcomingHolidays upcomingHolidays = modelMapper.map(upcomingHolidaysDTO, UpcomingHolidays.class);
        return upcomingHolidays;
    }
}
