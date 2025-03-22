package com.vgs.vocation_guidance.services;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.Batchifier;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.vgs.vocation_guidance.entity.AptitudeQuestion;
import com.vgs.vocation_guidance.repository.AptitudeQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AptitudeQuestionService {

    @Autowired
    private AptitudeQuestionRepository aptitudeQuestionRepository;

    public List<AptitudeQuestion> findAll() {
        return aptitudeQuestionRepository.findAll();
    }


    public List<AptitudeQuestion> getAptitudeQuestions(String interests) throws TranslateException, MalformedModelException, IOException {
        // Load a pre-trained Hugging Face model for text generation
        Model model = Model.newInstance("question-generation-model");
        model.load(Paths.get("src/main/resources/models"));

        // Create a translator for the model
        Translator<String, String> translator = new Translator<>() {
            @Override
            public NDList processInput(TranslatorContext ctx, String input) {
                NDManager manager = ctx.getNDManager();
                return new NDList(manager.create(input));
            }

            @Override
            public String processOutput(TranslatorContext ctx, NDList list) {
                return list.get(0).toString();
            }

            @Override
            public Batchifier getBatchifier() {
                return Batchifier.STACK;
            }
        };

        // Generate questions using the model
        try (Predictor<String, String> predictor = model.newPredictor(translator)) {
            String prompt = "Generate 5 aptitude questions related to: " + interests;
            String generatedText = predictor.predict(prompt);

            // Parse the generated text into individual questions
            List<String> rawQuestions = Arrays.asList(generatedText.split("\n"));

            // Map raw questions to AptitudeQuestion entities
            return mapToAptitudeQuestions(rawQuestions);
        }
    }

    private List<AptitudeQuestion> mapToAptitudeQuestions(List<String> rawQuestions) {
        List<AptitudeQuestion> aptitudeQuestions = new ArrayList<>();
        Random random = new Random();

        for (String rawQuestion : rawQuestions) {
            // Generate random options and correct answer for demonstration
            List<String> options = Arrays.asList("Option A", "Option B", "Option C", "Option D");
            String correctAnswer = options.get(random.nextInt(options.size()));

            // Create an AptitudeQuestion object
            AptitudeQuestion question = new AptitudeQuestion();
            question.setQuestionText(rawQuestion);
            question.setOptions(options);
            question.setCorrectAnswer(correctAnswer);

            aptitudeQuestions.add(question);
        }

        return aptitudeQuestions;
    }

}

