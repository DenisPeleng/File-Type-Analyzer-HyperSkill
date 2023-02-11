package analyzer;

import analyzer.algorithms.Algorithm;
import analyzer.algorithms.KMPAlgorithm;
import analyzer.algorithms.NaiveAlgorithm;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Wrong arguments");
        } else {
            long timeStart = System.nanoTime();
            String filePath = args[1];
            String searchType = args[2];
            String typeDescription = args[3];
            Algorithm algorithmTextProcess = new NaiveAlgorithm();
            switch (args[0]) {
                case "--naive" -> algorithmTextProcess = new NaiveAlgorithm();
                case "--KMP" -> algorithmTextProcess = new KMPAlgorithm();
            }
            System.out.println(algorithmTextProcess.execute(new File(filePath), searchType, typeDescription));
            long timeFinish = System.nanoTime();
            System.out.printf("It took %d seconds", timeFinish - timeStart);
        }
    }
}
