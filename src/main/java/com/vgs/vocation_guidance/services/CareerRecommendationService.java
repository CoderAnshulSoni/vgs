package com.vgs.vocation_guidance.services;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;
import com.vgs.vocation_guidance.entity.CareerRecommendation;
import com.vgs.vocation_guidance.repository.CareerRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerRecommendationService {

    @Autowired
    private CareerRecommendationRepository careerRecommendationRepository;

    public CareerRecommendation createRecommendation(CareerRecommendation recommendation) {
        return careerRecommendationRepository.save(recommendation);
    }

    public List<CareerRecommendation> getRecommendationsByUser(Long userId) {
        return careerRecommendationRepository.findByUserId(userId);
    }

    public String recommendCareer(String interests, String aptitudeResults) throws TranslateException {
        // Load pre-trained NLP model
        Model model = Model.newInstance("career-recommendation-model");
        Predictor<String, String> predictor = model.newPredictor();

        // Combine interests and aptitude results
        String input = interests + " " + aptitudeResults;

        // Get career recommendation
        return predictor.predict(input);
    }
}

