package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    private static final List<File> filesToProcess = new ArrayList<>();
    private static final List<FileFormat> patternsList = new ArrayList<>();

    public static List<File> getFilesToProcess() {
        return filesToProcess;
    }

    public static List<FileFormat> getPatternsList() {
        return patternsList;
    }

    public static void checkFiles(String[] args) {
        String folderPath = args[0];
        String patternsPath = args[1];
        parseDbFileFormats(patternsPath);
        getAllFilesFromChildFolders(folderPath);
        FileTypeAnalyzer.execute();
    }

    public static void getAllFilesFromChildFolders(String folder) {
        filesToProcess.clear();
        File folderFile = new File(folder);
        goInsideFolder(folderFile);
    }


    private static void goInsideFolder(File fileFolder) {
        if (fileFolder.isFile()) {
            filesToProcess.add(fileFolder.getAbsoluteFile());
        } else if (fileFolder.listFiles() != null) {
            for (File fileTmp : fileFolder.listFiles()
            ) {
                goInsideFolder(fileTmp);
            }
        }
    }

    public static void parseDbFileFormats(String path) {
        patternsList.clear();
        File DbFile = new File(path);
        try {
            List<String> dataDb = Files.readAllLines(DbFile.toPath());
            dataDb.forEach(tmp -> {
                String[] arrTmp = tmp.split(";");
                patternsList.add(new FileFormat(Integer.parseInt(arrTmp[0]), arrTmp[1].replace("\"", ""), arrTmp[2].replace("\"", "")));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
