package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollectWasteRequest {
    private String email;
    private String message;
    private Long agentId;
    private Category wasteCategory;
    private String username;
    private Double wasteWeigh;
}
