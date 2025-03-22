package com.vgs.vocation_guidance.ai_util;

import com.vgs.vocation_guidance.services.ModelTrainingService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ModelTrainingRunner implements CommandLineRunner {

    private final ModelTrainingService modelTrainingService;

    public ModelTrainingRunner(ModelTrainingService modelTrainingService) {
        this.modelTrainingService = modelTrainingService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Training the model...");
        modelTrainingService.trainAndSaveModel();
    }
}