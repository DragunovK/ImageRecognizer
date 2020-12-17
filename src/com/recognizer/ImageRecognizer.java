/*
 * Created by Kirill Dragunov on 16 dec. 2020 16:20:04
 */

package com.recognizer;

import com.config.Configuration;
import com.data_service.DataService;
import org.ejml.simple.SimpleMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class ImageRecognizer {
    NeuralNetwork neuralNetwork;

    private SimpleMatrix buildReferenceMatrix(ArrayList<SimpleMatrix> referenceImages) {
        SimpleMatrix result = referenceImages.get(0);

        for (int i = 1; i < referenceImages.size(); i++) {
            result = result.concatRows(referenceImages.get(i));
        }

        return result;
    }

    public ImageRecognizer(ArrayList<SimpleMatrix> referenceImages) {
        neuralNetwork = new NeuralNetwork(
                buildReferenceMatrix(referenceImages),
                Configuration.max_allowed_error,
                Configuration.epsilon
        );
    }

    public void recognize(String path) throws IOException {
        ArrayList<Integer> winnersIndexes = neuralNetwork.recognize(DataService.getImage(path));

        if (winnersIndexes.size() > 1) {
            System.out.println("Possible results: " + winnersIndexes);
            System.out.println("Result: " + winnersIndexes.get(ThreadLocalRandom.current().nextInt(winnersIndexes.size())));
        } else if (winnersIndexes.size() == 1) {
            System.out.println("Result: " + winnersIndexes.get(0));
        } else {
            System.out.println("Cannot recognize the image");
        }
    }
}
