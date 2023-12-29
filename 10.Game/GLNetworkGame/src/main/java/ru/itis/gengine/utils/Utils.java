package ru.itis.gengine.utils;

import java.io.*;

public class Utils {
    public static String readFromFile(String path) {
        StringBuilder builder = new StringBuilder();

        try (InputStream in = new FileInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load file!"
                    + System.lineSeparator() + ex.getMessage());
        }
        return builder.toString();
    }
}
