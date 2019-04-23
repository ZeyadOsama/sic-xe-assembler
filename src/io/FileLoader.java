package io;

import misc.constants.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class FileLoader {

    private String filePath;
    private String fileParentPath;
    private String fileName;

    public FileLoader() {
    }

    public FileLoader(String filePath) {
        this.filePath = filePath;
    }

    public BufferedReader loadFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Constants.TEXT_FILE, Constants.TEXT_FILE_FORMAT);
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        filePath = chooser.getSelectedFile().getAbsolutePath();
        fileParentPath = chooser.getSelectedFile().getParent();
        fileName = chooser.getSelectedFile().getName();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " + fileName);

            File file = new File(filePath);
            try {
                return new BufferedReader(new FileReader(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileParentPath() {
        return fileParentPath;
    }

    public String getFileName() {
        return fileName;
    }
}
