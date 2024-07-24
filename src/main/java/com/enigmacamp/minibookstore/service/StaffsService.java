package com.enigmacamp.minibookstore.service;

import model.dto.request.StaffRequest;
import model.dto.response.StaffResponse;

import java.util.List;

public interface StaffsService {
    StaffResponse createStaff(StaffRequest staffRequest);
    StaffResponse getStaffById(String id);
    List<StaffResponse> getAllStaffs();
    StaffResponse updateStaff(String id, StaffRequest staffRequest);
    void deleteStaffById(String id);
}
