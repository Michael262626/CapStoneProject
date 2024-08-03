package com.africa.semiclon.capStoneProject.dtos.request;

import com.africa.semiclon.capStoneProject.data.models.Category;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SendWasteDetailRequest {
    @Id
    private Long agentId;
    private String username;
    private Long userId;
    private Double wasteWeigh;
    private Category wasteCategory;


}
