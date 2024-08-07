package com.africa.semiclon.capStoneProject.service;

import com.africa.semiclon.capStoneProject.data.models.Category;
import com.africa.semiclon.capStoneProject.data.models.Points;
import com.africa.semiclon.capStoneProject.data.models.User;
import com.africa.semiclon.capStoneProject.data.models.Waste;
import com.africa.semiclon.capStoneProject.data.repository.PointRepository;
import com.africa.semiclon.capStoneProject.data.repository.UserRepository;
import com.africa.semiclon.capStoneProject.data.repository.WasteRepository;
import com.africa.semiclon.capStoneProject.dtos.request.AwardPointRequest;
import com.africa.semiclon.capStoneProject.dtos.response.AwardPointResponse;
import com.africa.semiclon.capStoneProject.services.interfaces.PointsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;



@SpringBootTest
public class PointServiceTest {
    @Autowired
    private PointsService pointsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    WasteRepository wasteRepository;
    @Autowired
    PointRepository pointsRepository;


    @Test
    public void testAdminCanAwardPointToUserAfterSellingWaste() {
        User user = new User();
        user.setUsername("user");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        user.setBalance(BigDecimal.ZERO);
        userRepository.save(user);

        Waste waste = new Waste();
        waste.setType(Category.PLASTIC);
        waste.setQuantity("10kg");
        wasteRepository.save(waste);

        Points points = new Points();
        points.setUserId(user.getUserId());
        points.setTotalPoints(BigDecimal.ZERO.setScale(2));
        points.setPointEarned(BigDecimal.ZERO.setScale(2));
        points.setPointRedeemed(BigDecimal.ZERO.setScale(2));
        points.setTotalPoints(BigDecimal.ZERO.setScale(2));
        pointsRepository.save(points);
        user.setPoints(points);
        userRepository.save(user);
        AwardPointRequest request = new AwardPointRequest();
        request.setUserId(user.getUserId());
        request.setWasteId(waste.getWasteId());
        AwardPointResponse response = pointsService.awardPoint(request);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Points awarded successfully");
        assertThat(response.getAwardedPoints()).isEqualByComparingTo(BigDecimal.valueOf(100.00).setScale(2));
        User updatedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPoints().getTotalPoints()).isEqualByComparingTo(BigDecimal.valueOf(100.00).setScale(2));
    }

}