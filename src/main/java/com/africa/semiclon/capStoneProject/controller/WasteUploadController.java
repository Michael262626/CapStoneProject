package com.africa.semiclon.capStoneProject.controller;

import com.africa.semiclon.capStoneProject.dtos.request.UploadWasteRequest;
import com.africa.semiclon.capStoneProject.services.interfaces.WasteService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/waste")
public class WasteUploadController {
    private final WasteService wasteService;
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?>uploadWaste(@ModelAttribute UploadWasteRequest uploadWasteRequest){
        return ResponseEntity.status(CREATED).body(wasteService.upload(uploadWasteRequest));
    }
}
