package analyzer.algorithms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class NaiveAlgorithm implements Algorithm {

    @Override
    public String execute(File file, String pattern, String description) {
        String UNKNOWN_FORMAT = "Unknown file type";
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            String fileAsString = new String(data);
            if (fileAsString.contains(pattern)) {
                return description;
            } else {

                return UNKNOWN_FORMAT;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
