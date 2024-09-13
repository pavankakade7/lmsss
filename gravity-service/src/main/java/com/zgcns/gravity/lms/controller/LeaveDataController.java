package com.zgcns.gravity.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgcns.gravity.lms.dto.LeaveDataDTO;
import com.zgcns.gravity.lms.services.LeaveDataService;

@RestController
@RequestMapping("/api/leave-data")
public class LeaveDataController {

    @Autowired
    private LeaveDataService leaveDataService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<LeaveDataDTO> getLeaveData(@PathVariable("userId") Long userId) {
        LeaveDataDTO leaveDataDTO = leaveDataService.getLeaveDataByUserId(userId);
        return ResponseEntity.ok(leaveDataDTO);
    }
    
    @PostMapping
    public ResponseEntity<LeaveDataDTO> createLeaveData(@RequestBody LeaveDataDTO leaveDataDTO) {
        LeaveDataDTO savedLeaveDataDTO = leaveDataService.saveLeaveData(leaveDataDTO);
        return ResponseEntity.ok(savedLeaveDataDTO);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<LeaveDataDTO> updateLeaveData(@PathVariable("userId") Long userId, @RequestBody LeaveDataDTO leaveDataDTO) {
        LeaveDataDTO updatedLeaveDataDTO = leaveDataService.updateLeaveData(userId, leaveDataDTO);
        return ResponseEntity.ok(updatedLeaveDataDTO);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteLeaveData(@PathVariable Long userId) {
        leaveDataService.deleteLeaveData(userId);
        return ResponseEntity.noContent().build();
    }
}
