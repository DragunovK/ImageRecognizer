/*
 * Created by Kirill Dragunov on 16 dec. 2020 14:03:29
 */

package com.data_service;

import com.config.Configuration;
import com.data_service.file_service.ImageService;
import org.ejml.simple.SimpleMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class DataService {
    private static SimpleMatrix serialize(BufferedImage image) {
        SimpleMatrix serializedImage = new SimpleMatrix(1, image.getWidth() * image.getHeight());

        int serializedImageIndex = 0;
        for (int j = 0; j < image.getWidth(); j++) {
            for (int k = 0; k < image.getHeight(); k++) {
                Color color = new Color(image.getRGB(j, k));

                if ((color.getRed() == 0) && (color.getBlue() == 0) && (color.getGreen() == 0))
                    serializedImage.set(serializedImageIndex++, 1);
                else
                    serializedImage.set(serializedImageIndex++, -1);
            }
        }

        return serializedImage;
    }

    public static ArrayList<SimpleMatrix> getReferenceImages() throws IOException {
        ArrayList<SimpleMatrix> referenceImages = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            BufferedImage image = ImageService.read(Configuration.input_clean_image_path + i + ".jpg");
            referenceImages.add(serialize(image));
        }

        return referenceImages;
    }

    public static SimpleMatrix getImage(String path) throws IOException {
        BufferedImage image = ImageService.read(path);
        return serialize(image);
    }
}
