package analyzer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class DirectoryProcessing {
    private static final List<File> filesToProcess = new ArrayList<>();

    public static List<File> getAllFilesFromChildFolders(String folder) {
        File folderFile = new File(folder);
        goInsideFolder(folderFile);
        return filesToProcess;
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
}
