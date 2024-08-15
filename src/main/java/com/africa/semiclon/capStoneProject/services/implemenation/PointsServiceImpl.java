package com.africa.semiclon.capStoneProject.services.implemenation;

import com.africa.semiclon.capStoneProject.data.models.Points;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.PointRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.AwardPointRequest;
import com.africa.semiclon.capStoneProject.dtos.response.AwardPointResponse;
import com.africa.semiclon.capStoneProject.dtos.response.WeightCollectedResponse;
import com.africa.semiclon.capStoneProject.exception.UserNotFoundException;
import com.africa.semiclon.capStoneProject.exception.WasteNotFoundException;
import com.africa.semiclon.capStoneProject.services.interfaces.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PointsServiceImpl implements PointsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WasteRepository wasteRepository;

    @Autowired
    private PointRepository pointsRepository;
    @Override
    public AwardPointResponse awardPoint(AwardPointRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Waste waste = wasteRepository.findById(request.getWasteId()).orElseThrow(() -> new WasteNotFoundException("Waste not found"));
        BigDecimal pointsEarned = calculatePoints(waste);
        Points points = user.getPoints();
        if (points == null) {
            points = new Points();
            points.setUserId(user.getUserId());
            points.setTotalPoints(BigDecimal.ZERO);
            points.setPointEarned(BigDecimal.ZERO);
            points.setPointRedeemed(BigDecimal.ZERO);
        }
        points.setPointEarned(points.getPointEarned().add(pointsEarned));
        points.setTotalPoints(points.getTotalPoints().add(pointsEarned));
        pointsRepository.save(points);
        user.setPoints(points);
        userRepository.save(user);
        AwardPointResponse response = new AwardPointResponse();
        response.setMessage("Points awarded successfully");
        response.setAwardedPoints(pointsEarned);
        response.setTotalPoints(points.getTotalPoints());

        return response;
    }



    private BigDecimal calculatePoints(Waste waste) {
        BigDecimal quantityInKg = new BigDecimal(waste.getQuantity());
        BigDecimal pointsPerKg = new BigDecimal("10");
        return quantityInKg.multiply(pointsPerKg);
    }
}
