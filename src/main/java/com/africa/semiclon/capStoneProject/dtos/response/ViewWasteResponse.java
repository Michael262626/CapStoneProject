package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.data.models.Waste;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ViewWasteResponse {
    private List<Waste> wastes;

}
