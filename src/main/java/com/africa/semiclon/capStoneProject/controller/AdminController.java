package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.ManageUsersRequest;
import com.africa.semiclon.capStoneProject.dtos.request.ViewWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.response.ManageUserResponse;
import com.africa.semiclon.capStoneProject.dtos.response.ViewWasteResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/manageUser")
    public ResponseEntity<ManageUserResponse> manageUser(@RequestBody ManageUsersRequest manageUsersRequest) {
        ManageUserResponse response = adminService.manageUsers(manageUsersRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/viewAllWaste")
    public ResponseEntity<ViewWasteResponse> viewAllWaste(@RequestBody ViewWasteRequest viewWasteRequest) {
        ViewWasteResponse response = adminService.viewAllWaste(viewWasteRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
