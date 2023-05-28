package com.humancloud.Employeemanagementsystem.Controller;

import com.humancloud.Employeemanagementsystem.DTO.AuthRequest;
import com.humancloud.Employeemanagementsystem.DTO.EmployeeDTO;
import com.humancloud.Employeemanagementsystem.DTO.LeaveResponseDTO;
import com.humancloud.Employeemanagementsystem.DTO.LeavesDTO;
import com.humancloud.Employeemanagementsystem.Service.EmployeeService;
import com.humancloud.Employeemanagementsystem.Service.JwtService;
import com.humancloud.Employeemanagementsystem.Service.LeavesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmployeeController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private LeavesServices leavesServices;





    @PostMapping("/create")
    public ResponseEntity<String> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        String employee = this.employeeService.createEmployee(employeeDTO);
        return new ResponseEntity<String>(employee, HttpStatus.CREATED);
    }


    // Genereate Token and Login


    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }


    }



@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/update/{empId}")
    public ResponseEntity<String> updateEmployee(@PathVariable Integer empId, @RequestBody EmployeeDTO employeeDTO) {
        String employee = this.employeeService.updateEmployee(empId, employeeDTO);
        return new ResponseEntity<String>(employee, HttpStatus.OK);
    }

    @GetMapping("/allemp")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployee() {
        List<EmployeeDTO> allEmployee = this.employeeService.getAllEmployee();
        return ResponseEntity.ok(allEmployee);
    }
    @GetMapping("/search-employee/{keyword}")
    public ResponseEntity<List<EmployeeDTO>> searchEmployee(@PathVariable String keyword) {
        List<EmployeeDTO> employeeDTOS = this.employeeService.searchEmployee(keyword);
        return ResponseEntity.ok(employeeDTOS);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeDTO> getSingleEmployee(@PathVariable Integer empId) {
        EmployeeDTO singleEmployee = this.employeeService.getSingleEmployee(empId);
        return ResponseEntity.ok(singleEmployee);
    }



    @DeleteMapping("/delete/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Integer empId) {
        String message = this.employeeService.deleteEmployee(empId);
        return new ResponseEntity<String>(message, HttpStatus.OK);
    }


    @GetMapping("/leaves/{empId}")
    public ResponseEntity<List<LeaveResponseDTO>> getallEmployeeLeaves(@PathVariable Integer empId) {
        List<LeaveResponseDTO> leaveByUserID = this.leavesServices.getLeaveByUserID(empId);
        return ResponseEntity.ok(leaveByUserID);

    }

    @PostMapping("/apply-leave/{empId}")
    public ResponseEntity<String> applyLeaves(@PathVariable Integer empId, @RequestBody LeavesDTO leavesDTO) {
        String leavesDto = this.leavesServices.createLeaves(empId, leavesDTO);
        return new ResponseEntity<>(leavesDto, HttpStatus.CREATED);
    }

    @PutMapping("/update-leave/{leaveId}")
    public ResponseEntity<String> updateLeaveApplication(@PathVariable Integer leaveId, @RequestBody LeavesDTO leavesDTO) {
        String updateLeaves = this.leavesServices.updateLeaves(leaveId, leavesDTO);
        return new ResponseEntity<>(updateLeaves, HttpStatus.OK);

    }


    @GetMapping("/pending-leaves/{empId}")
    public  ResponseEntity<List<LeaveResponseDTO>> getPendingLeavesOfEmployee(@PathVariable Integer empId){
        List<LeaveResponseDTO> allPendingLeavesOfEmployee = this.leavesServices.getAllPendingLeavesOfEmployee(empId);
        return ResponseEntity.ok(allPendingLeavesOfEmployee);
    }






}
