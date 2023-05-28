package com.humancloud.Employeemanagementsystem.Service.Impl;

import com.humancloud.Employeemanagementsystem.DTO.EmployeeDTO;
import com.humancloud.Employeemanagementsystem.Entity.Employee;
import com.humancloud.Employeemanagementsystem.Exceptions.ResourceNotFoundException;
import com.humancloud.Employeemanagementsystem.Repository.EmployeeRepository;
import com.humancloud.Employeemanagementsystem.Service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository  employeeRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public String createEmployee(EmployeeDTO employeeDTO) {

        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));


        this.employeeRepository.save(employee);
        return "Hurray !! Employee Register Successfully !!!";
    }

    @Override
    public String updateEmployee(Integer empId,EmployeeDTO employeeDTO) {
        Employee employee = this.employeeRepository.findById(empId).orElseThrow(()->new ResourceNotFoundException("employee","empId ",empId));
        employee.setMobile(employeeDTO.getMobile()!=null ? employeeDTO.getMobile() : employee.getMobile());
        employee.setName(employeeDTO.getName()!=null ? employeeDTO.getName() : employee.getName() );
        employee.setEmail(employeeDTO.getEmail()!=null ? employeeDTO.getEmail() : employee.getEmail() );
        employee.setPassword(employeeDTO.getPassword()!=null ? employeeDTO.getPassword() : employee.getPassword() );
        this.employeeRepository.save(employee);
        return "Hurray!! Employee updated SuccessFully";
    }

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        List<Employee> employeeList = this.employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOS = employeeList.stream().map(emp -> this.modelMapper.map(emp, EmployeeDTO.class)).collect(Collectors.toList());
        return  employeeDTOS;
    }

    @Override
    public EmployeeDTO getSingleEmployee(Integer empId) {
        Employee employee = this.employeeRepository.findById(empId).orElseThrow(()->new ResourceNotFoundException("employee","empId ",empId));
        return this.modelMapper.map(employee,EmployeeDTO.class);

    }

    @Override
    public String deleteEmployee(Integer empId) {
        Employee employee = this.employeeRepository.findById(empId).orElseThrow(()->new ResourceNotFoundException("employee","empId ",empId));
        this.employeeRepository.delete(employee);
        return "Employee Deleted Successfully";
    }

    @Override
    public List<EmployeeDTO> searchEmployee(String name) {
        List<Employee> searchedEmployee = this.employeeRepository.findByNameContaining(name);
        List<EmployeeDTO> employeeDTOS = searchedEmployee.stream().map(employee  -> modelMapper.map(employee, EmployeeDTO.class)).collect(Collectors.toList());
        return employeeDTOS;
    }




}
