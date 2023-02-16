package analyzer;

import analyzer.algorithms.Algorithm;
import analyzer.algorithms.RabinKarpAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FileTypeAnalyzer {
    public static void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Algorithm algorithmTextProcess = new RabinKarpAlgorithm();
        List<Callable<String>> callables = new ArrayList<>();
        List<File> filesToProcess = FileUtils.getFilesToProcess();
        List<FileFormat> patternsList = FileUtils.getPatternsList();
        algorithmTextProcess.setPatternList(patternsList);
        for (File tmp : filesToProcess
        ) {
            callables.add(() -> algorithmTextProcess.execute(tmp));


        }

        List<Future<String>> futures;
        try {
            futures = executor.invokeAll(callables);
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }
}
