package io;

import assembler.core.ErrorHandler;
import assembler.core.ObjectCodeGenerator;
import assembler.core.OutputGenerator;
import assembler.core.Program;
import misc.utils.ConsoleColors;

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
        String file = generateFileName("addresses");
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            for (String string : OutputGenerator.getAddressFileLines())
                writer.write(string + "\n");
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
        acceptMessage("Address file written successfully to " + file);
    }

    public void writeSymbolFile() {
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not write symbol table file due to parsing errors.");
            return;
        }
        String file = generateFileName("symbol-table");
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            for (String string : OutputGenerator.getSymbolFileLines())
                writer.write(string + "\n");
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
        acceptMessage("Symbol table file written successfully to " + file);
    }

    public void writeObjectCodeFile(ObjectCodeGenerator objectCodeGenerator) {
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not write object code file due to parsing errors.");
            return;
        }
        String file = generateFileName("object-code");
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            writer.write(objectCodeGenerator.getHeaderRecord());
            writer.write(objectCodeGenerator.getTextRecords());
            writer.write(objectCodeGenerator.getEndRecord());
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
        acceptMessage("Symbol table file written successfully to " + file);
    }

    private String generateFileName(String fileName) {
        return outputGenerator.getFilePath() + "/"
                + outputGenerator.getFileName().replaceAll(".txt", "")
                + "-" + fileName + ".txt";
    }

    private void acceptMessage(String message) {
        System.out.println(ConsoleColors.GREEN + message + ConsoleColors.RESET);
    }
}
