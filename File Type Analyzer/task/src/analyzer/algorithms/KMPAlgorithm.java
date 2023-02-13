package analyzer.algorithms;

import analyzer.FileFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;


public class KMPAlgorithm implements Algorithm {
    private List<FileFormat> patternList;

    @Override
    public String execute(File file) {
        String UNKNOWN_FORMAT = "Unknown file type";
        FileFormat resultFileFormat = new FileFormat(0, UNKNOWN_FORMAT, UNKNOWN_FORMAT);
        for (FileFormat fileFormat : patternList
        ) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                String fileAsString = new String(data);
                if (KMPSearch(fileFormat.pattern(), fileAsString)) {
                    if (fileFormat.weight() > resultFileFormat.weight()) {
                        resultFileFormat = fileFormat;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return file.getName() + ": " + resultFileFormat.description();
    }

    public void setPatternList(List<FileFormat> patternList) {
        this.patternList = patternList;
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
