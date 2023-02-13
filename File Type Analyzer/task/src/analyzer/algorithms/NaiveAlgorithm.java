package analyzer.algorithms;

import analyzer.FileFormat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class NaiveAlgorithm implements Algorithm {
    private List<FileFormat> patternList;

    @Override
    public void setPatternList(List<FileFormat> patternList) {
        this.patternList = patternList;
    }

    @Override
    public String execute(File file) {
        String UNKNOWN_FORMAT = "Unknown file type";
        FileFormat resultFileFormat = new FileFormat(0, UNKNOWN_FORMAT, UNKNOWN_FORMAT);
        for (FileFormat fileFormat : patternList
        ) {
            try {
                byte[] data = Files.readAllBytes(file.toPath());
                String fileAsString = new String(data);
                if (fileAsString.contains(fileFormat.pattern())) {
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


}
