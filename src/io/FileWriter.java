package io;

import assembler.core.ErrorHandler;
import assembler.core.OutputGenerator;
import assembler.core.Program;

import java.io.IOException;

public final class FileWriter {

    private OutputGenerator outputGenerator;

    public FileWriter(OutputGenerator outputGenerator) {
        this.outputGenerator = outputGenerator;
    }

    public void writeAddressFile() {
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not write address file due to parsing errors.");
            return;
        }

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
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not write symbol file due to parsing errors.");
            return;
        }

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
