package io;

import assembler.core.Program;
import misc.constants.Constants;
import misc.utils.ConsoleColors;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public final class FileLoader {

    private String filePath;
    private String fileParentPath;
    private String fileName;

    public FileLoader() {
    }

    public FileLoader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Opens a file chooser dialogue to facilitate searching for
     * target file.
     * Sets the file path to be used lately.
     */
    public void openChooserDialogue() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Constants.TEXT_FILE, Constants.TEXT_FILE_FORMAT);
        chooser.setFileFilter(filter);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            filePath = chooser.getSelectedFile().getAbsolutePath();
            fileParentPath = chooser.getSelectedFile().getParent();
            fileName = chooser.getSelectedFile().getName();
            System.out.println(ConsoleColors.GREEN + "File opened successfully" + ConsoleColors.RESET);
        } else System.exit(-1);
    }

    /**
     * @return ArrayList containing un-parsed {String} line by line
     */
    public ArrayList<String> loadFile() {
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));
            while ((line = bufferedReader.readLine()) != null)
                Program.getInstructionsList().add(line);
        } catch (IOException e) {
            e.getCause();
        }
        return Program.getInstructionsList();
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
