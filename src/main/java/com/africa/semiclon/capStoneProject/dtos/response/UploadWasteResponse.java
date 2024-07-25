package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadWasteResponse {
    private Long wasteId;
    @JsonProperty("waste_url")
    private String url;
    @JsonProperty("waste_description")
    private String description;
    private Category category;

}
