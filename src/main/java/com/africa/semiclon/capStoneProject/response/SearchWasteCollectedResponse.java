package com.africa.semiclon.capStoneProject.response;

import com.africa.semiclon.capStoneProject.data.models.Category;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
public class SearchWasteCollectedResponse {
    private String username;
    private Category wasteCategory;
    private Double wasteWeight;
    private LocalDateTime dateAndTimeCollected;

    public SearchWasteCollectedResponse(Long id, Category wasteCategory, Double wasteWeigh, String username, LocalDateTime dateAndTimeCollected) {
        this.username = username;
        this.wasteCategory = wasteCategory;
        this.wasteWeight = wasteWeigh;
        this.dateAndTimeCollected = dateAndTimeCollected;
    }
}
