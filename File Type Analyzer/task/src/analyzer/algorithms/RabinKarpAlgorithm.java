package analyzer.algorithms;

import analyzer.FileFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;

public class RabinKarpAlgorithm implements Algorithm {
    private List<FileFormat> patternList;
    private final HashMap<FileFormat, Integer> patternHashMap = new HashMap<>();

    @Override
    public String execute(File file) {
        String UNKNOWN_FORMAT = "Unknown file type";
        FileFormat resultFileFormat = new FileFormat(0, UNKNOWN_FORMAT, UNKNOWN_FORMAT);
        for (FileFormat fileFormat : patternList
        ) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                String fileAsString = new String(data);
                for (int i = 0; i < fileAsString.length(); i++) {
                    int endIndex = i + fileFormat.pattern().length();
                    endIndex = Math.min(endIndex, fileAsString.length());
                    String substring = fileAsString.substring(i, endIndex);
                    int currentHash = computeHash(substring);
                    if (currentHash == patternHashMap.get(fileFormat)) {
                        if (substring.equals(fileFormat.pattern())) {
                            resultFileFormat = fileFormat;
                        }
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
        for (FileFormat formatTmp : patternList
        ) {
            int hash = computeHash(formatTmp.pattern());
            patternHashMap.put(formatTmp, hash);
        }
    }

    private int computeHash(String subString) {
        int a = 3;
        int m = 28;
        char[] charStr = subString.toCharArray();
        int result = 0;
        for (int i = 0; i < charStr.length; i++) {
            result += (int) (charStr[i] * Math.pow(a, i));
        }
        return result % m;
    }

}
