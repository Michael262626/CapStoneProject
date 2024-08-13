package com.africa.semiclon.capStoneProject.exception;

import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CollectWasteResponse {

    private String message;
    private Long agentId;
    private Long userId;
    private Category wasteCategory;
    private String userName;
    private Double wasteWeigh;
}
