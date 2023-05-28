package com.humancloud.Employeemanagementsystem.Controller;

import com.humancloud.Employeemanagementsystem.DTO.LeaveResponseDTO;
import com.humancloud.Employeemanagementsystem.DTO.LeavesDTO;
import com.humancloud.Employeemanagementsystem.Service.LeavesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private LeavesServices leavesServices;
    @PutMapping("/approve-leave/leave/{leaveId}/manager/{reportingManagerId}")
    public ResponseEntity<String> approveLeave(@PathVariable Integer leaveId, @PathVariable Integer reportingManagerId){
        String leavesDTO = this.leavesServices.approveLeave(leaveId, reportingManagerId);
        return new ResponseEntity<String>(leavesDTO, HttpStatus.ACCEPTED);
    }
    @PutMapping("/reject-leave/leave/{leaveId}/manager/{reportingManagerId}")
    public ResponseEntity<String> rejectLeave(@PathVariable Integer leaveId, @PathVariable Integer reportingManagerId){
        String leavesDTO = this.leavesServices.rejectLeave(leaveId, reportingManagerId);
        return new ResponseEntity<String>(leavesDTO, HttpStatus.ACCEPTED);
    }

    @GetMapping("/pending-leaves-requests/{reportingMangerId}")
    public  ResponseEntity<List<LeaveResponseDTO>> leaveApprovalRequestsToManager(@PathVariable Integer reportingMangerId){
        List<LeaveResponseDTO> allPendingLeavesOfEmployee = this.leavesServices.getAllPendingLeavesOfEmployee(reportingMangerId);
        return ResponseEntity.ok(allPendingLeavesOfEmployee);
    }

}
