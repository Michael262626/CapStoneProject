package com.africa.semiclon.capStoneProject.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GenerateWasteReportRequest {
    private Long adminId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;


}
