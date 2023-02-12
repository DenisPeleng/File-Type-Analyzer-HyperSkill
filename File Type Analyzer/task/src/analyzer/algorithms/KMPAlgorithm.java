package analyzer.algorithms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class KMPAlgorithm implements Algorithm {
    @Override
    public String execute(File file, String pattern, String description) {
        String UNKNOWN_FORMAT = "Unknown file type";
        try {
            byte[] data = Files.readAllBytes(file.toPath());
            String fileAsString = new String(data);
            if (KMPSearch(pattern, fileAsString)) {
                return file.getName() + ": " + description;
            } else {
                return file.getName() + ": " + UNKNOWN_FORMAT;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean KMPSearch(String pattern, String txt) {
        int patternLength = pattern.length();
        int txtLength = txt.length();
        int j = 0;
        int[] longestPrefix = computeLPSArray(pattern, patternLength);
        int i = 0;
        while (i < txtLength) {
            if (pattern.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == patternLength) {
                return true;
            } else if (i < txtLength && pattern.charAt(j) != txt.charAt(i)) {
                if (j != 0) j = longestPrefix[j - 1];
                else i = i + 1;
            }
        }
        return false;
    }


    private int[] computeLPSArray(String pattern, int patternLength) {
        int[] longestPrefix = new int[patternLength];
        int length = 0;
        int i = 1;
        longestPrefix[0] = 0;
        while (i < patternLength) {
            if (pattern.charAt(i) == pattern.charAt(length)) {
                length++;
                longestPrefix[i] = length;
                i++;
            } else {
                if (length != 0) {
                    length = longestPrefix[length - 1];
                } else {
                    longestPrefix[i] = length;
                    i++;
                }
            }
        }
        return longestPrefix;
    }
}
