package assembler.core;

import assembler.structure.ErrorHandler;
import assembler.structure.Symbol;
import assembler.tables.SymbolTable;
import misc.constants.Constants;
import parser.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Responsible for generating addresses file and symbol table file
 */
public final class OutputGenerator {

    private static ErrorHandler errorHandler = ErrorHandler.getInstance();

    private String filePath;
    private String fileName;

    private static ArrayList<String> addressFileLines = new ArrayList<>();
    private static ArrayList<String> symbolFileLines = new ArrayList<>();

    /**
     * @param filePath parsed file absolute parent path
     * @param fileName parsed file name
     */
    public OutputGenerator(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public static ArrayList<String> getAddressFileLines() {
        return addressFileLines;
    }

    public static ArrayList<String> getSymbolFileLines() {
        return symbolFileLines;
    }

    public void generateAddressFile() {
        LocationCounter locationCounter = LocationCounter.getInstance();
        for (int i = 0; i < locationCounter.getProgramCounter(); i++) {
            addressFileLines.add(locationCounter.getHexAddresses().get(i)
                    + "\t\t"
                    + Parser.getInstance().getInstructions().get(i));
        }
    }

    public void generateSymbolFile() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        for (String name : symbolTable.get().keySet()) {
            Symbol symbol = symbolTable.getSymbol(name);
            String string = symbol.getAddress() + "\t\t" + symbol.getLabel() + "\t\t";
            if (symbol.hasValue())
                string += symbol.getValue();
            System.out.println(string);
            symbolFileLines.add(string);
        }
    }

    public static void update() {
        addressFileLines.add(
                LocationCounter.getInstance().getCurrentAddress()
                        + Constants.TAB
                        + Parser.getInstance().getCurrentInstruction());

        if (errorHandler.hasError())
            addressFileLines.add(errorHandler.generate());
    }

    public void showInTerminal() {
        for (String line : addressFileLines)
            System.out.println(line);
    }

    public void makeAddressFile() {
        String file = filePath + "/" + fileName.replaceAll(".txt", "") + "-addresses.txt";
        System.out.println(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (String string : addressFileLines) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public void makeSymbolFile() {
        String file = filePath + "/" + fileName.replaceAll(".txt", "") + "-symbol-table.txt";
        System.out.println(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (String string : symbolFileLines) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }

    }
}
