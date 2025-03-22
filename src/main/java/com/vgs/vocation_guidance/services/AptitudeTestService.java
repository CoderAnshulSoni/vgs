package com.vgs.vocation_guidance.services;

import com.vgs.vocation_guidance.entity.AptitudeQuestion;
import com.vgs.vocation_guidance.repository.AptitudeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AptitudeTestService {

    @Autowired
    private AptitudeQuestionRepository aptitudeQuestionRepository;

    public List<AptitudeQuestion> generateTest(int numberOfQuestions) {
        return aptitudeQuestionRepository.findAll().subList(0, numberOfQuestions);
    }

    public double evaluateTest(List<String> userAnswers) {
        List<AptitudeQuestion> questions = aptitudeQuestionRepository.findAll();
        int correctAnswers = 0;

        for (int i = 0; i < userAnswers.size(); i++) {
            if (userAnswers.get(i).equals(questions.get(i).getCorrectAnswer())) {
                correctAnswers++;
            }
        }

        return (double) correctAnswers / userAnswers.size();
    }
}