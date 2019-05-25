package io;

import assembler.core.ErrorHandler;
import assembler.core.ObjectCodeGenerator;
import assembler.core.OutputGenerator;
import assembler.core.Program;
import misc.utils.Terminal;

import java.io.IOException;

/**
 * Responsible for writing outputs in files if needed
 */
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
            for (String string : OutputGenerator.getInstance().getAddressFileLines())
                writer.write(string + "\n");
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
        Terminal.acceptMessage("Address file written successfully to " + file);
    }

    public void writeSymbolFile() {
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not write symbol table file due to parsing errors.");
            return;
        }
        String file = generateFileName("symbol-table");
        try {
            java.io.FileWriter writer = new java.io.FileWriter(file);
            for (String string : OutputGenerator.getInstance().getSymbolFileLines())
                writer.write(string + "\n");
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
        Terminal.acceptMessage("Symbol table file written successfully to " + file);
    }

    /**
     * @param objectCodeGenerator which contains all records
     */
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
        Terminal.acceptMessage("Symbol table file written successfully to " + file);
    }

    private String generateFileName(String fileName) {
        return outputGenerator.getFilePath() + "/"
                + outputGenerator.getFileName().replaceAll(".txt", "")
                + "-" + fileName + ".txt";
    }
}
