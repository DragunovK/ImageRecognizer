/*
 * Created by Kirill Dragunov on 16 dec. 2020 13:59:26
 */

package com.config;

public class Configuration {
    public static final double max_allowed_error = 0.01;
    public static final double epsilon = 0.099;

    public static final String input_clean_image_path =
            System.getProperty("user.dir") + "\\Images\\Clean\\";

    public static final String input_noisy_image_path =
            System.getProperty("user.dir") + "\\Images\\Noisy\\";
}
