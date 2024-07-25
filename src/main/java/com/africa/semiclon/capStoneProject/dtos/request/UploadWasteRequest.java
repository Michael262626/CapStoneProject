package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadWasteRequest {
    private Long userId;
    private MultipartFile mediaFile;
    private Category category;
    private String description;

}
