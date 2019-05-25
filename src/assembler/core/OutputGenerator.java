package assembler.core;

import assembler.structure.Symbol;
import assembler.tables.SymbolTable;
import misc.constants.Constants;
import misc.utils.Terminal;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Converter.Decimal.toHexadecimal;
import static misc.utils.Utils.addHexadecimalNotation;

/**
 * Responsible for generating addresses file and symbol table file
 */
public final class OutputGenerator implements Assembler.Interface {

    /**
     * Singleton class if no parameters are passed
     * <p>
     * Otherwise @see {@link OutputGenerator#OutputGenerator(String filePath, String fileName) Constructor}
     */
    private static OutputGenerator instance = new OutputGenerator();
    private static ErrorHandler errorHandler = ErrorHandler.getInstance();

    /**
     * @param filePath parsed file absolute parent path
     * @param fileName parsed file name
     */
    public OutputGenerator(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
    }

    private String filePath;
    private String fileName;
    private ArrayList<String> addressFileLines = new ArrayList<>();
    private ArrayList<String> symbolFileLines = new ArrayList<>();
    /**
     * Terminal instance
     */
    public Terminal terminal = new Terminal() {
        @Override
        public void print() {
            printAddressFile();
            printSymbolFile();
        }

        private void printAddressFile() {
            headerMessage("Address File");
            for (String line : addressFileLines)
                System.out.println(line);
        }

        private void printSymbolFile() {
            headerMessage("Symbol Table File");
            for (String line : symbolFileLines)
                System.out.println(line);
        }
    };

    private OutputGenerator() {

    }

    public static OutputGenerator getInstance() {
        return instance;
    }

    public ArrayList<String> getAddressFileLines() {
        return addressFileLines;
    }

    public ArrayList<String> getSymbolFileLines() {
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

    @Override
    public void reset() {
        addressFileLines.clear();
        symbolFileLines.clear();
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void update() {
        addressFileLines.add(
                LocationCounter.getInstance().getCurrentConvertedAddress()
                        + Constants.TAB
                        + Parser.getInstance().getCurrentInstruction());

        if (errorHandler.hasError())
            addressFileLines.add(errorHandler.generate());
    }
}
