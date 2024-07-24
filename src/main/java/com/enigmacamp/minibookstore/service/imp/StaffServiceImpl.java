package com.enigmacamp.minibookstore.service.imp;

import com.enigmacamp.minibookstore.repository.StaffsRepository;
import com.enigmacamp.minibookstore.service.StaffsService;
import model.dto.request.StaffRequest;
import model.dto.response.StaffResponse;
import model.entity.Staffs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StaffServiceImpl implements StaffsService {

    private final StaffsRepository staffsRepository;

    @Autowired
    public StaffServiceImpl(StaffsRepository staffsRepository) {
        this.staffsRepository = staffsRepository;
    }

    @Override
    public StaffResponse createStaff(StaffRequest staffRequest) {
        Staffs staff = new Staffs();
        staff.setName(staffRequest.getName());
        staff.setEmail(staffRequest.getEmail());
        staff.setPassword(staffRequest.getPassword());
        staff.setPosition(Staffs.StaffPosition.valueOf(staffRequest.getPosition().toUpperCase()));

        Staffs savedStaff = staffsRepository.insert(staff);
        return StaffResponse.fromEntity(savedStaff);
    }

    @Override
    public StaffResponse getStaffById(String id) {
        return staffsRepository.findById(id)
                .map(StaffResponse::fromEntity)
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + id));
    }

    @Override
    public List<StaffResponse> getAllStaffs() {
        return staffsRepository.findAll().stream()
                .map(StaffResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public StaffResponse updateStaff(String id, StaffRequest staffRequest) {
        Staffs existingStaff = staffsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with ID: " + id));

        existingStaff.setName(staffRequest.getName());
        existingStaff.setEmail(staffRequest.getEmail());
        existingStaff.setPassword(staffRequest.getPassword());
        existingStaff.setPosition(Staffs.StaffPosition.valueOf(staffRequest.getPosition().toUpperCase()));

        Staffs updatedStaff = staffsRepository.update(existingStaff);
        return StaffResponse.fromEntity(updatedStaff);
    }

    @Override
    public void deleteStaffById(String id) {
        staffsRepository.deleteById(id);
    }
}
