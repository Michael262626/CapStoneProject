package com.africa.semiclon.capStoneProject.utils;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.africa.semiclon.capStoneProject.dtos.request.UploadWasteRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import static com.africa.semiclon.capStoneProject.data.models.Category.PLASTIC;

public class TestUtils {
    public static UploadWasteRequest buildUploadWasteRequest(InputStream inputStream) throws IOException {
        UploadWasteRequest request = new UploadWasteRequest();
        MultipartFile file = new MockMultipartFile("waste", inputStream);
        request.setMediaFile(file);
        request.setCategory(PLASTIC);
        request.setUserId(2L);

        return request;


    }
}
