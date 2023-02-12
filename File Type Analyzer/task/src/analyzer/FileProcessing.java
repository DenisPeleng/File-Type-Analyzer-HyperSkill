package analyzer;

import analyzer.algorithms.Algorithm;
import analyzer.algorithms.KMPAlgorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class FileProcessing {
    public static void checkFiles(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        String folderPath = args[0];
        String searchType = args[1];
        String typeDescription = args[2];
        Algorithm algorithmTextProcess = new KMPAlgorithm();
        List<File> filesToProcess = DirectoryProcessing.getAllFilesFromChildFolders(folderPath);
        List<Callable<String>> callables = new ArrayList<>();
        for (File tmp : filesToProcess
        ) {
            callables.add(() -> algorithmTextProcess.execute(tmp, searchType, typeDescription));

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
