package com.humancloud.Employeemanagementsystem.Controller;

import com.humancloud.Employeemanagementsystem.DTO.LeaveResponseDTO;
import com.humancloud.Employeemanagementsystem.DTO.LeavesDTO;
import com.humancloud.Employeemanagementsystem.Service.LeavesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class adminController {

    @Autowired
    private LeavesServices leavesServices;


    @GetMapping("/getall-leaves")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<LeaveResponseDTO>> getAllLeaves(){
        List<LeaveResponseDTO> allLeaves = this.leavesServices.getAllLeaves();
        return new ResponseEntity<List<LeaveResponseDTO>>(allLeaves, HttpStatus.OK);

    }
    @GetMapping("/leave/{leaveId}")
    public  ResponseEntity<LeaveResponseDTO> getLeaveById(@PathVariable Integer leaveId){
        LeaveResponseDTO leaveById = this.leavesServices.getLeaveById(leaveId);
        return  ResponseEntity.ok(leaveById);

    }




}
