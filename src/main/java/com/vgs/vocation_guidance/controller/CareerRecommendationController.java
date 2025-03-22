package com.vgs.vocation_guidance.controller;

import com.vgs.vocation_guidance.entity.CareerRecommendation;
import com.vgs.vocation_guidance.services.CareerRecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career-recommendations")
public class CareerRecommendationController {

    @Autowired
    private CareerRecommendationService careerRecommendationService;

    @PostMapping
    public CareerRecommendation createRecommendation(@RequestBody CareerRecommendation recommendation) {
        return careerRecommendationService.createRecommendation(recommendation);
    }

    @GetMapping("/user/{userId}")
    public List<CareerRecommendation> getRecommendationsByUser(@PathVariable Long userId) {
        return careerRecommendationService.getRecommendationsByUser(userId);
    }
}
