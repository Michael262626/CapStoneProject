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

    @Test
    public void testAwardPointsToUserWithExistingPoints() {
        User user = new User();
        user.setUsername("user1");
        user.setEmail("testuser@example1.com");
        user.setPassword("password1");
        user.setBalance(BigDecimal.ZERO);
        userRepository.save(user);

        Points points = new Points();
        points.setUserId(user.getUserId());
        points.setTotalPoints(BigDecimal.valueOf(50.00).setScale(2));
        points.setPointEarned(BigDecimal.valueOf(50.00).setScale(2));
        points.setPointRedeemed(BigDecimal.ZERO.setScale(2));
        pointsRepository.save(points);

        user.setPoints(points);
        userRepository.save(user);

        Waste waste = new Waste();
        waste.setType(Category.POLYTHENEBAG);
        waste.setQuantity("2kg");
        wasteRepository.save(waste);

        AwardPointRequest request = new AwardPointRequest();
        request.setUserId(user.getUserId());
        request.setWasteId(waste.getWasteId());

        AwardPointResponse response = pointsService.awardPoint(request);

        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Points awarded successfully");
        assertThat(response.getAwardedPoints()).isEqualByComparingTo(BigDecimal.valueOf(20.00).setScale(2));

        User updatedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPoints().getTotalPoints()).isEqualByComparingTo(BigDecimal.valueOf(70.00).setScale(2));
    }

    @Test
    public void testAwardPointsToUserWithZeroPoints() {
        User user = new User();
        user.setUsername("user2");
        user.setEmail("testuser@example2.com");
        user.setPassword("password2");
        user.setBalance(BigDecimal.ZERO);
        userRepository.save(user);
        User savedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getPoints()).isNull();

        Points points = new Points();
        points.setUserId(user.getUserId());
        points.setTotalPoints(BigDecimal.valueOf(50.00).setScale(2));
        points.setPointEarned(BigDecimal.valueOf(50.00).setScale(2));
        points.setPointRedeemed(BigDecimal.ZERO.setScale(2));
        pointsRepository.save(points);
        user.setPoints(points);
        userRepository.save(user);
        Waste waste = new Waste();
        waste.setType(Category.POLYTHENEBAG);
        waste.setQuantity("2kg");
        wasteRepository.save(waste);
        AwardPointRequest request = new AwardPointRequest();
        request.setUserId(user.getUserId());
        request.setWasteId(waste.getWasteId());

        AwardPointResponse response = pointsService.awardPoint(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isEqualTo("Points awarded successfully");
        assertThat(response.getAwardedPoints()).isEqualByComparingTo(BigDecimal.valueOf(20.00).setScale(2));
        User updatedUser = userRepository.findById(user.getUserId()).orElse(null);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPoints().getTotalPoints()).isEqualByComparingTo(BigDecimal.valueOf(70.00).setScale(2));
    }


}