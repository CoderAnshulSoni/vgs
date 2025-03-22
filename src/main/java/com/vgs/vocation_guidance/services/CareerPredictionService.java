package com.vgs.vocation_guidance.services;

import ai.djl.MalformedModelException;
import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.translate.TranslateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;

@Service
public class CareerPredictionService {

    public String predictCareer(float userData) throws TranslateException, MalformedModelException, IOException {
        try (NDManager manager = NDManager.newBaseManager()) {
            // Load the model
            Model model = Model.newInstance("linear-regression");
            model.load(Paths.get("src/main/resources/models"));

            // Prepare input data
            NDArray input = manager.create(userData);

            // Make a prediction
            try (Predictor<NDArray, NDArray> predictor = model.newPredictor()) {
                NDArray output = predictor.predict(input);
                return output.toFloatArray()[0] + "";
            }
        }
    }
}