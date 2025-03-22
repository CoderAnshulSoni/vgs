package com.vgs.vocation_guidance.controller;

import ai.djl.MalformedModelException;
import ai.djl.translate.TranslateException;
import com.vgs.vocation_guidance.services.CareerPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    @Autowired
    private CareerPredictionService predictionService;

    @PostMapping
    public String predictCareer(@RequestBody float[] userData) {
        try {
            return predictionService.predictCareer(userData);
        } catch (TranslateException | MalformedModelException | IOException e) {
            e.printStackTrace();
            return "Prediction failed";
        }
    }
}