package com.vgs.vocation_guidance.controller;

import com.vgs.vocation_guidance.entity.AptitudeQuestion;
import com.vgs.vocation_guidance.services.AptitudeQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/aptitude-questions")
public class AptitudeQuestionController {

    @Autowired
    private AptitudeQuestionService aptitudeQuestionService;

    @GetMapping
    public List<AptitudeQuestion> getAllQuestions() {
        return aptitudeQuestionService.findAll();
    }
}
