package io;

import assembler.core.Assembler;
import misc.constants.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public void loadFile() throws IOException {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Constants.TEXT_FILE, Constants.TEXT_FILE_FORMAT);
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        String filePath = chooser.getSelectedFile().getAbsolutePath();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());

            File file = new File(filePath);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bufferedReader.readLine()) != null)
                Assembler.addProgramLine(line);
        }
    }
}
