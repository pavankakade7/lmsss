package com.zgcns.gravity.lms.converter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zgcns.gravity.lms.dto.EmployeeDTO;
import com.zgcns.gravity.lms.model.Employee;

@Component
public class EmployeeDTOConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public EmployeeDTO convertEmployeetoEMployeeDTO(Employee employee) {
		EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
		System.out.println("EmployeeDTO: " + employeeDTO);
		return employeeDTO;
	}

	
	
	public Employee convertEmployeeDTOtoEmployee(EmployeeDTO employeeDTO) {
		Employee employee = modelMapper.map(employeeDTO, Employee.class);
		return employee;
	}
}
