package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.UploadWasteRequest;
import com.africa.semiclon.capStoneProject.dtos.response.UploadWasteResponse;

public interface WasteService {
    UploadWasteResponse upload(UploadWasteRequest request);

}
