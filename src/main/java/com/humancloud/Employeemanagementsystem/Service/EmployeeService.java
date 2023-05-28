package com.humancloud.Employeemanagementsystem.Service;

import com.humancloud.Employeemanagementsystem.DTO.EmployeeDTO;

import java.util.List;

public interface EmployeeService {

    String createEmployee(EmployeeDTO employeeDTO);

    String updateEmployee(Integer empId, EmployeeDTO employeeDTO);

    List<EmployeeDTO> getAllEmployee();

    EmployeeDTO getSingleEmployee(Integer empId);

    String deleteEmployee(Integer empId);
    List <EmployeeDTO> searchEmployee(String name);
}