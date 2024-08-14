package com.africa.semiclon.capStoneProject.response;

import com.africa.semiclon.capStoneProject.data.models.Agent;
import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ViewWasteCollectedResponse {
    private String username;


    private Double wasteWeigh;
    private Category wasteCategory;
    private Agent agentId;
    private String message;

    public ViewWasteCollectedResponse(String message){
        this.message = message;
    }

    public ViewWasteCollectedResponse(Category wasteCategory, LocalDateTime dateAndTimeCollected, Agent agentId, Double wasteWeigh) {
        this.wasteCategory = wasteCategory;
        this.agentId = agentId;
        this.wasteWeigh = wasteWeigh;
    }
}

