package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.AwardPointRequest;
import com.africa.semiclon.capStoneProject.dtos.request.CreateUserRequest;
import com.africa.semiclon.capStoneProject.dtos.response.AwardPointResponse;
import com.africa.semiclon.capStoneProject.dtos.response.CreateUserResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.PointsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/points")
@AllArgsConstructor
public class PointServiceController {
    @Autowired
    PointsService pointsService;

    @PostMapping("/points")
    public ResponseEntity<AwardPointResponse> awardPointToUser(@RequestBody AwardPointRequest awardPointRequest) {
        AwardPointResponse response = pointsService.awardPoint(awardPointRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
