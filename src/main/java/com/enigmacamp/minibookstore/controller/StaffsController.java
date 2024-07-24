package com.enigmacamp.minibookstore.controller;

import com.enigmacamp.minibookstore.config.APIUrl;
import com.enigmacamp.minibookstore.service.imp.StaffServiceImpl;
import model.dto.request.StaffRequest;
import model.dto.response.StaffResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(APIUrl.STAFFS_API_URL)
public class StaffsController {
    private final StaffServiceImpl staffService;

    @Autowired
    public StaffsController(StaffServiceImpl staffService) {
        this.staffService = staffService;
    }

    @PostMapping
    public ResponseEntity<StaffResponse> createStaff(@RequestBody StaffRequest staffRequest) {
        StaffResponse staffResponse = staffService.createStaff(staffRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(staffResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable String id) {
        StaffResponse staffResponse = staffService.getStaffById(id);
        return ResponseEntity.ok(staffResponse);
    }

    @GetMapping
    public ResponseEntity<List<StaffResponse>> getAllStaffs() {
        List<StaffResponse> staffResponses = staffService.getAllStaffs();
        return ResponseEntity.ok(staffResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffResponse> updateStaff(@PathVariable String id, @RequestBody StaffRequest staffRequest) {
        StaffResponse staffResponse = staffService.updateStaff(id, staffRequest);
        return ResponseEntity.ok(staffResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable String id) {
        staffService.deleteStaffById(id);
        return ResponseEntity.noContent().build();
    }
}
