package analyzer.algorithms;

import analyzer.FileFormat;

import java.io.File;
import java.util.List;

public interface Algorithm {
    String execute(File file);

    void setPatternList(List<FileFormat> patternList);
}
