package cn.com.blueInfo.business;

import org.junit.Test;

import java.io.File;

public class TestFolderFiles {

    @Test
    public void readFiles() {
        String folderPath = "Z:".concat(File.separator).concat("LenoveBookData").concat(File.separator)
                .concat("D").concat(File.separator).concat("Development Program").concat(File.separator)
                .concat("ProjectCode").concat(File.separator);
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        if (files != null) {
            for (File oneFile : files) {
                System.out.println(oneFile.getName());
            }
        }
        searchFiles(folder);
    }

    public void searchFiles(File folder) {
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) {
            return;
        }
        for (File file : listOfFiles) {
            if (file.isFile()) {
                String filePath = file.getAbsolutePath();
                if (filePath.contains("HttpClient.java")) {
                    System.out.println(filePath);
                }
            } else if (file.isDirectory()) {
                searchFiles(file);
            }
        }
    }

}
