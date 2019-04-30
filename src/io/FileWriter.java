package io;

import assembler.core.OutputGenerator;

import java.io.IOException;

public class FileWriter {

    OutputGenerator outputGenerator;

    public FileWriter(OutputGenerator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }

    public void writeAddressFile() {
        String file = outputGenerator.getFilePath() + "/"
                + outputGenerator.getFileName().replaceAll(".txt", "") + "-addresses.txt";
        System.out.println(file);
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            for (String string : OutputGenerator.getAddressFileLines()) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public void writeSymbolFile() {
        String file = outputGenerator.getFilePath() + "/"
                + outputGenerator.getFileName().replaceAll(".txt", "") + "-symbol-table.txt";
        System.out.println(file);
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            for (String string : OutputGenerator.getSymbolFileLines()) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public void writeObjectCodeFile() {
        // TODO
    }
}
