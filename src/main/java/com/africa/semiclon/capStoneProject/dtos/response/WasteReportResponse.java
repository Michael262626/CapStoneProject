package com.africa.semiclon.capStoneProject.dtos.response;

import com.africa.semiclon.capStoneProject.data.models.WasteReport;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WasteReportResponse {
    private List<WasteReport> reportItems;
    private String message;
}
