package io;

import misc.constants.Constants;
import parser.Parser;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {

    public void loadFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                Constants.TEXT_FILE, Constants.TEXT_FILE_FORMAT);
        chooser.setFileFilter(filter);

        int returnVal = chooser.showOpenDialog(null);
        String filePath = chooser.getSelectedFile().getAbsolutePath();

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println(filePath);
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());

            try {
                File file = new File(filePath);
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                Parser parser = Parser.getInstance();
                String line;
                while ((line = bufferedReader.readLine()) != null)
                    parser.parseInstruction(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
