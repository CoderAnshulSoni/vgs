package com.vgs.vocation_guidance.services;

import ai.djl.Model;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
import ai.djl.nn.Block;
import ai.djl.nn.SequentialBlock;
import ai.djl.nn.core.Linear;
import ai.djl.training.DefaultTrainingConfig;
import ai.djl.training.Trainer;
import ai.djl.training.dataset.ArrayDataset;
import ai.djl.training.dataset.Batch;
import ai.djl.training.dataset.Dataset;
import ai.djl.training.loss.Loss;
import ai.djl.training.optimizer.Optimizer;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ModelTrainingService {

    public void trainAndSaveModel() {
        try (NDManager manager = NDManager.newBaseManager()) {
            // Create a simple dataset
            NDArray x = manager.create(new float[]{1, 2, 3, 4, 5}).reshape(new Shape(5, 1)); // Shape: (batch_size, input_dim)
            NDArray y = manager.create(new float[]{2, 4, 6, 8, 10}).reshape(new Shape(5, 1)); // Shape: (batch_size, output_dim)

            // Create a model
            Model model = Model.newInstance("linear-regression");

            // Define the model block
            Block block = new SequentialBlock()
                    .add(Linear.builder().setUnits(1).build()); // Input dimension is inferred, output dimension is 1
            model.setBlock(block);

            // Configure training
            DefaultTrainingConfig config = new DefaultTrainingConfig(Loss.l2Loss())
                    .optOptimizer(Optimizer.sgd().optWeightDecays(0.01f).build());

            // Train the model
            try (Trainer trainer = model.newTrainer(config)) {
                // Initialize the model parameters
                trainer.initialize(new Shape(1)); // Input dimension = 1

                // Create a dataset
                ArrayDataset dataset = new ArrayDataset.Builder()
                        .setData(x)
                        .optLabels(y)
                        .setSampling(5, false) // Batch size = 5, no shuffling
                        .build();

                // Training loop
                for (int epoch = 0; epoch < 10; epoch++) {
                    for (Batch batch : trainer.iterateDataset(dataset)) {
                        trainer.iterateDataset((Dataset) batch);
                    }
                    System.out.println("Epoch " + epoch + " completed.");
                }
            }

            // Ensure the directory exists
            Files.createDirectories(Paths.get("src/main/resources/models"));

            // Save the model
            model.save(Paths.get("src/main/resources/models"), "linear-regression");
            System.out.println("Model saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}