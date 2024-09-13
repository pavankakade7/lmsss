package com.zgcns.gravity.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zgcns.gravity.lms.dto.UpcomingHolidaysDTO;
import com.zgcns.gravity.lms.services.UpcomingHolidaysService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/upcoming-holidays")
public class UpcomingHolidayController {

    private final UpcomingHolidaysService upcomingHolidaysService;

    @Autowired
    public UpcomingHolidayController(UpcomingHolidaysService upcomingHolidaysService) {
        this.upcomingHolidaysService = upcomingHolidaysService;
    }

    @GetMapping
    public ResponseEntity<List<UpcomingHolidaysDTO>> getAllUpcomingHolidays() {
        List<UpcomingHolidaysDTO> holidays = upcomingHolidaysService.getAllUpcomingHolidays();
        return ResponseEntity.ok(holidays); // Returning the list of holidays with OK status
    }

    @GetMapping("/{id}")
    public ResponseEntity<UpcomingHolidaysDTO> getUpcomingHolidaysById(@PathVariable("id") Long id) {
        Optional<UpcomingHolidaysDTO> upcomingHolidaysDTO = upcomingHolidaysService.getUpcomingHolidaysById(id);
        return upcomingHolidaysDTO
                .map(ResponseEntity::ok) // Directly using ResponseEntity.ok for a successful response
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UpcomingHolidaysDTO> createUpcomingHolidays(@RequestBody UpcomingHolidaysDTO upcomingHolidaysDTO) {
        UpcomingHolidaysDTO savedHoliday = upcomingHolidaysService.saveUpcomingHolidays(upcomingHolidaysDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedHoliday); // Status CREATED for new resource
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpcomingHolidaysDTO> updateUpcomingHolidays(@PathVariable("id") Long id, 
                                                                      @RequestBody UpcomingHolidaysDTO upcomingHolidaysDTO) {
        try {
            UpcomingHolidaysDTO updatedHoliday = upcomingHolidaysService.updateUpcomingHolidays(id, upcomingHolidaysDTO);
            return ResponseEntity.ok(updatedHoliday);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUpcomingHoliday(@PathVariable("id") Long id) {
        if (upcomingHolidaysService.getUpcomingHolidaysById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        upcomingHolidaysService.deleteUpcomingHolidayById(id);
        return ResponseEntity.noContent().build(); // No content status for a successful delete
    }
}
