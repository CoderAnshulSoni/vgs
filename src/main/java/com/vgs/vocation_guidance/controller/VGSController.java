package com.vgs.vocation_guidance.controller;

import ai.djl.MalformedModelException;
import ai.djl.translate.TranslateException;
import com.vgs.vocation_guidance.entity.AptitudeQuestion;
import com.vgs.vocation_guidance.entity.CareerRecommendation;
import com.vgs.vocation_guidance.services.AptitudeQuestionService;
import com.vgs.vocation_guidance.services.CareerPredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
public class VGSController {

    @Autowired
    private AptitudeQuestionService aptitudeQuestionService;

    @Autowired
    private CareerPredictionService careerPredictionService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @PostMapping("/start-test")
    public String startTest(@RequestParam String name, @RequestParam String email,
                            @RequestParam int age, @RequestParam String educationLevel,
                            @RequestParam String interests,
                            Model model) throws TranslateException, MalformedModelException, IOException {
        // Save user details (you can save to the database if needed)
        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("age", age);
        model.addAttribute("educationLevel", educationLevel);

        // Fetch aptitude questions from the service layer
        List<AptitudeQuestion> questions = aptitudeQuestionService.getAptitudeQuestions(interests);
        model.addAttribute("questions", questions);

        return "test";
    }

    @PostMapping("/submit-test")
    public String submitTest(@RequestParam List<String> answer, Model model) {
        // Evaluate the test
        float score = (float) evaluateTest(answer);

        // Call the machine learning model for career recommendations
        String recommendations;
        try {
            recommendations = careerPredictionService.predictCareer(score);
        } catch (TranslateException e) {
            recommendations = Arrays.asList(
                    new CareerRecommendation(1L, 1L, "Error generating recommendations", 0.0)
            ).toString();
        } catch (MalformedModelException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        model.addAttribute("recommendations", recommendations);
        return "result";
    }

    private double evaluateTest(List<String> answers) {
        // Simple evaluation logic (you can replace this with your own logic)
        int correctAnswers = 0;
        for (String answer : answers) {
            if (answer.equals("4") || answer.equals("Paris")) {
                correctAnswers++;
            }
        }
        return (float) correctAnswers / answers.size();
    }
}