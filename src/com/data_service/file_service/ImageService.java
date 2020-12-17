/*
 * Created by Kirill Dragunov on 16 dec. 2020 13:54:56
 */

package com.data_service.file_service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageService {
    public static BufferedImage read(String path) throws IOException {
        BufferedImage image = null;

        image = ImageIO.read(new File(path));

        return image;
    }
}
