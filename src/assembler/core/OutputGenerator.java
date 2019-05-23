package assembler.core;

import assembler.structure.Symbol;
import assembler.tables.SymbolTable;
import misc.constants.Constants;
import misc.utils.ConsoleColors;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Converter.Decimal.toHexadecimal;
import static misc.utils.Utils.addHexadecimalNotation;

/**
 * Responsible for generating addresses file and symbol table file
 */
public final class OutputGenerator {

    private String filePath;
    private String fileName;

    private static ArrayList<String> addressFileLines = new ArrayList<>();
    private static ArrayList<String> symbolFileLines = new ArrayList<>();

    private static ErrorHandler errorHandler = ErrorHandler.getInstance();

    /**
     * @param filePath parsed file absolute parent path
     * @param fileName parsed file name
     */
    public OutputGenerator(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    public OutputGenerator() {
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
                    + Program.getInstructionsList().get(i));
        }
    }

    public void generateSymbolFile() {
        SymbolTable symbolTable = SymbolTable.getInstance();
        for (String name : symbolTable.get().keySet()) {
            Symbol symbol = symbolTable.getSymbol(name);
            String string = addHexadecimalNotation(toHexadecimal(symbol.getAddress()))
                    + Constants.TAB + symbol.getLabel() + Constants.TAB;
            symbolFileLines.add(string);
        }
    }

    public static void update() {
        addressFileLines.add(
                LocationCounter.getInstance().getCurrentConvertedAddress()
                        + Constants.TAB
                        + Parser.getInstance().getCurrentInstruction());

        if (errorHandler.hasError())
            addressFileLines.add(errorHandler.generate());
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }


    public Terminal terminal = new Terminal();

    /**
     * Utility class to print files content in terminal
     */
    public class Terminal {

        private Terminal() {
        }

        public void showAddressFile() {
            headerMessage("Address File");
            for (String line : addressFileLines)
                System.out.println(line);
        }

        public void showSymbolFile() {
            headerMessage("Symbol Table File");
            for (String line : symbolFileLines)
                System.out.println(line);
        }

        private void headerMessage(String message) {
            System.out.println(ConsoleColors.PURPLE + message + ConsoleColors.RESET);
        }
    }
}
