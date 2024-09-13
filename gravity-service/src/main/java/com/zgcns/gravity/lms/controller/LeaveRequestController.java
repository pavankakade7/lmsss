package com.zgcns.gravity.lms.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zgcns.gravity.lms.dto.EmployeeDTO;
import com.zgcns.gravity.lms.dto.LeaveRequestDTO;
import com.zgcns.gravity.lms.model.Employee;
import com.zgcns.gravity.lms.model.RequestStatus;
import com.zgcns.gravity.lms.services.EmployeeService;
import com.zgcns.gravity.lms.services.LeaveDataService;
import com.zgcns.gravity.lms.services.LeaveRequestService;
import com.zgcns.gravity.lms.services.UserService;
import com.zgcns.gravity.lms.converter.LeaveRequestDTOConverter;

@RestController
@RequestMapping("/api/leave-requests")
public class LeaveRequestController {

    @Autowired
    private final LeaveRequestService leaveRequestService;
    @Autowired
    private final EmployeeService employeeService;
    @Autowired
    private final LeaveDataService leaveDataService;
    @Autowired
    private final UserService userService;
    @Autowired
    private final LeaveRequestDTOConverter leaveRequestDTOConverter;

    public LeaveRequestController(LeaveRequestService leaveRequestService, EmployeeService employeeService,
                                  LeaveDataService leaveDataService, UserService userService,
                                  LeaveRequestDTOConverter leaveRequestDTOConverter) {
        this.leaveRequestService = leaveRequestService;
        this.employeeService = employeeService;
        this.leaveDataService = leaveDataService;
        this.userService = userService;
        this.leaveRequestDTOConverter = leaveRequestDTOConverter;
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests() {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getAllLeaveRequests();
        return new ResponseEntity<>(leaveRequests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequestById(@PathVariable("id") Long id) {
        return leaveRequestService.getLeaveRequestById(id)
                .map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@RequestBody LeaveRequestDTO leaveRequestDTO) {
        LeaveRequestDTO savedLeaveRequest = leaveRequestService.saveLeaveRequest(leaveRequestDTO);
        return new ResponseEntity<>(savedLeaveRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@PathVariable("id") Long id,
                                                               @RequestBody LeaveRequestDTO leaveRequestDTO) {
        Optional<LeaveRequestDTO> existingLeaveRequest = leaveRequestService.getLeaveRequestById(id);
        if (existingLeaveRequest.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Update the fields that are provided in the request body
        LeaveRequestDTO existingRequest = existingLeaveRequest.get();
        if (leaveRequestDTO.getStatus() != null) {
            existingRequest.setStatus(leaveRequestDTO.getStatus());
        }

        LeaveRequestDTO updatedLeaveRequest = leaveRequestService.updateLeaveRequest(id, existingRequest);
        return new ResponseEntity<>(updatedLeaveRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable("id") Long id) {
        if (!leaveRequestService.getLeaveRequestById(id).isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        leaveRequestService.deleteLeaveRequestById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<LeaveRequestDTO>> getLeaveRequestsByEmployee(@PathVariable("employeeId") Long employeeId) {
        Optional<EmployeeDTO> employee = employeeService.getEmployeeById(employeeId);
        if (employee.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getLeaveRequestsByEmployee(employee.get());
        return new ResponseEntity<>(leaveRequests, HttpStatus.OK);
    }

    @GetMapping("/user/{empId}")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequestByEmployeeId(@PathVariable("empId") Long empId) {
        List<LeaveRequestDTO> leaveRequests = leaveRequestService.getAllLeaveRequestByEmployeeId(empId);
        if (leaveRequests != null && !leaveRequests.isEmpty()) {
            return new ResponseEntity<>(leaveRequests, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{leaveId}/status")
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequestStatus(
            @PathVariable("leaveId") Long leaveId,
            @RequestBody Map<String, String> statusUpdate) {

        RequestStatus status = RequestStatus.valueOf(statusUpdate.get("requestStatus"));
        LeaveRequestDTO updatedRequest = leaveRequestService.updateLeaveRequestStatus(leaveId, status);
        if (updatedRequest.getStatus().equals(status)) {
            leaveDataService.updateLeaveDataApprove(updatedRequest.getUserId(), updatedRequest);
        }

        return ResponseEntity.ok(updatedRequest);
    }
}
