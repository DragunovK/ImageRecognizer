/*
 * Created by Kirill Dragunov on 16 dec. 2020 13:49:58
 */

package com;

import com.config.Configuration;
import com.data_service.DataService;
import com.recognizer.ImageRecognizer;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ImageRecognizer imageRecognizer = new ImageRecognizer(DataService.getReferenceImages());
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) break;

            imageRecognizer.recognize(Configuration.input_noisy_image_path + input);
        }
    }
}
