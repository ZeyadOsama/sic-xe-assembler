package io;

import assembler.structure.Operation;
import assembler.structure.OperationTable;
import misc.constants.Constants;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Loader {

    public OperationTable loadFile() throws IOException {
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
            BufferedReader br = new BufferedReader(new FileReader(file));

            Scanner sc = new Scanner(file);
//            String line;
//            while ((line = br.readLine()) != null) {
//                System.out.println(line);
//            }

            OperationTable operationTable = OperationTable.getInstance();
            Operation operation;
            while (sc.hasNext()) {
                System.out.println(sc.next());
            }


            return operationTable;
        }
        return null;
    }
}
