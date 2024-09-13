package com.zgcns.gravity.lms.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zgcns.gravity.lms.converter.EmployeeDTOConverter;
import com.zgcns.gravity.lms.dto.EmployeeDTO;
import com.zgcns.gravity.lms.model.Employee;
import com.zgcns.gravity.lms.repositories.EmployeeRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeDTOConverter employeeDTOConverter;

    public List<EmployeeDTO> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(employeeDTOConverter::convertEmployeetoEMployeeDTO)
                .collect(Collectors.toList());
    }

    public Optional<EmployeeDTO> getEmployeeById(Long id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        return employee.map(employeeDTOConverter::convertEmployeetoEMployeeDTO);
    }

   

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOConverter.convertEmployeeDTOtoEmployee(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return employeeDTOConverter.convertEmployeetoEMployeeDTO(savedEmployee);
    }

    public EmployeeDTO updateEmployee(Long empId, EmployeeDTO employeeDTO) {
        Employee employee = employeeDTOConverter.convertEmployeeDTOtoEmployee(employeeDTO);
        Employee updatedEmployee = employeeRepository.findById(empId)
                .map(emp -> {
                    emp.setFirstName(employee.getFirstName());
                    emp.setLastName(employee.getLastName());
                    emp.setEmail(employee.getEmail());
                    emp.setPhone(employee.getPhone());
                    emp.setTitle(employee.getTitle());
                    emp.setDepartment(employee.getDepartment());
                    emp.setGender(employee.getGender());
                    emp.setUser(employee.getUser());
                    return employeeRepository.save(emp);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with ID: " + empId));

        return employeeDTOConverter.convertEmployeetoEMployeeDTO(updatedEmployee);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}