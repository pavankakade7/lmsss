package com.zgcns.gravity.lms.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.lms.converter.EmployeeDTOConverter;
import com.zgcns.gravity.lms.converter.LeaveRequestDTOConverter;
import com.zgcns.gravity.lms.dto.EmployeeDTO;
import com.zgcns.gravity.lms.dto.LeaveRequestDTO;
import com.zgcns.gravity.lms.exception.ResourceNotFoundException;
import com.zgcns.gravity.lms.model.Employee;
import com.zgcns.gravity.lms.model.LeaveData;
import com.zgcns.gravity.lms.model.LeaveRequest;
import com.zgcns.gravity.lms.model.RequestStatus;
import com.zgcns.gravity.lms.repositories.EmployeeRepository;
import com.zgcns.gravity.lms.repositories.LeaveDataRepository;
import com.zgcns.gravity.lms.repositories.LeaveRequestRepository;
import jakarta.transaction.Transactional;

@Service
public class LeaveRequestService {

    @Autowired
    private final LeaveRequestRepository leaveRequestRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private LeaveDataRepository leaveDataRepository;			
    @Autowired
    private LeaveRequestDTOConverter leaveRequestDTOConverter;
    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    public LeaveRequestService(LeaveRequestRepository leaveRequestRepository, EmployeeRepository employeeRepository, LeaveDataRepository leaveDataRepository, LeaveRequestDTOConverter leaveRequestDTOConverter, EmployeeDTOConverter employeeDTOConverter) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.employeeRepository = employeeRepository;
        this.leaveDataRepository = leaveDataRepository;
        this.leaveRequestDTOConverter = leaveRequestDTOConverter;
        this.employeeDTOConverter = employeeDTOConverter;
    }

    public List<LeaveRequestDTO> getAllLeaveRequests() {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAll();
        return leaveRequests.stream()
                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO)
                .toList();
    }

    public Optional<LeaveRequestDTO> getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO);
    }

    @Transactional
    public LeaveRequestDTO saveLeaveRequest(LeaveRequestDTO leaveRequestDTO) {
        Long empId = leaveRequestDTO.getEmployeeDTO().getEmpId();
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + empId));

        LeaveRequest leaveRequest = leaveRequestDTOConverter.convertLeaveRequestDTOtoLeaveRequest(leaveRequestDTO);
        leaveRequest.setEmployee(employee);
        leaveRequest.setUserId(employee.getEmpId());
        return leaveRequestDTOConverter.convertLeaveRequesttoLeaveRequestDTO(leaveRequestRepository.save(leaveRequest));
    }

    public LeaveRequestDTO updateLeaveRequest(Long id, LeaveRequestDTO leaveRequestDTO) {
        LeaveRequest leaveRequest = leaveRequestDTOConverter.convertLeaveRequestDTOtoLeaveRequest(leaveRequestDTO);
        leaveRequest.setLeaveId(id); // Ensure the ID is set
        return leaveRequestDTOConverter.convertLeaveRequesttoLeaveRequestDTO(leaveRequestRepository.save(leaveRequest));
    }

    public void deleteLeaveRequestById(Long id) {
        leaveRequestRepository.deleteById(id);
    }

    public List<LeaveRequestDTO> getLeaveRequestsByEmployee(EmployeeDTO employeeDTO) {
        // Convert EmployeeDTO to Employee
        Employee employee = employeeDTOConverter.convertEmployeeDTOtoEmployee(employeeDTO);

        // Fetch the Employee entity from the repository
        Employee foundEmployee = employeeRepository.findById(employee.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // Fetch leave requests by the found employee
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployee(foundEmployee);

        // Convert LeaveRequest entities to LeaveRequestDTOs and return the list
        return leaveRequests.stream()
                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO)
                .toList();
    }

    public List<LeaveRequestDTO> getAllLeaveRequestByEmployeeId(Long empId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAllByEmployee_EmpId(empId);
        return leaveRequests.stream()
                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO)
                .toList();
    }

    public List<LeaveRequestDTO> getAllLeaveRequestByUserId(Long userId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findAllLeaveRequestByUserId(userId);
        return leaveRequests.stream()
                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO)
                .toList();
    }

    public LeaveRequestDTO updateLeaveRequestStatus(Long leaveId, RequestStatus status) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found"));
        leaveRequest.setStatus(status);
        return leaveRequestDTOConverter.convertLeaveRequesttoLeaveRequestDTO(leaveRequestRepository.save(leaveRequest));
    }

    public void adjustLeaveData(LeaveRequest leaveRequest) {
        LeaveData leaveData = leaveDataRepository.findByUser_UserId(leaveRequest.getEmployee().getEmpId())
                .orElseThrow(() -> new ResourceNotFoundException("LeaveData not found"));
        switch (leaveRequest.getLeaveType()) {
            case "casualLeaves":
                leaveData.setCasualLeaves(leaveData.getCasualLeaves() - 1);
                break;
            case "medicalLeaves":
                leaveData.setMedicalLeaves(leaveData.getMedicalLeaves() - 1);
                break;
            case "privilegedLeaves":
                leaveData.setPrivilegedLeaves(leaveData.getPrivilegedLeaves() - 1);
                break;
            case "unpaidLeaves":
                leaveData.setUnpaidLeaves(leaveData.getUnpaidLeaves() - 1);
                break;
            default:
                break;
        }
        leaveDataRepository.save(leaveData);
    }

//    public LeaveRequestDTO findByLeaveId(Long leaveId) {
//        // Retrieve the LeaveRequest as an Optional
//        Optional<LeaveRequest> leaveRequestOpt = Optional.of(leaveRequestRepository.findByLeaveId(leaveId));
//
//        // Convert the LeaveRequest to LeaveRequestDTO if present, otherwise handle the absence
//        return leaveRequestOpt
//                .map(leaveRequestDTOConverter::convertLeaveRequesttoLeaveRequestDTO)
//                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found with ID: " + leaveId));
//    }
    
    /// optional method for above 
    public LeaveRequestDTO findByLeaveId(Long leaveId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveId(leaveId)
                .orElseThrow(() -> new ResourceNotFoundException("LeaveRequest not found with ID: " + leaveId));

        return leaveRequestDTOConverter.convertLeaveRequesttoLeaveRequestDTO(leaveRequest);
    }
    
    
}
