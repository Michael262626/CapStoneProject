package com.africa.semiclon.capStoneProject.services.interfaces;

import com.africa.semiclon.capStoneProject.dtos.request.AwardPointRequest;
import com.africa.semiclon.capStoneProject.dtos.response.AwardPointResponse;

public interface PointsService {

    AwardPointResponse awardPoint(AwardPointRequest request);

}
