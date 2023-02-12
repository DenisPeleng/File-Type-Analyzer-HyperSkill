package analyzer.algorithms;

import java.io.File;

public interface Algorithm {
    String execute(File file, String pattern, String description);
}
