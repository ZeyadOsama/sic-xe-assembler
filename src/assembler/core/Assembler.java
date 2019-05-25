package assembler.core;

import assembler.tables.SymbolTable;
import parser.Parser;

/**
 * Have access to all core package classes
 */
public final class Assembler {

    public static void reset() {
        Program.reset();
        Parser.getInstance().reset();
        ErrorHandler.getInstance().reset();
        LocationCounter.getInstance().reset();
        SymbolTable.getInstance().reset();
        ObjectCodeGenerator.getInstance().reset();
        OutputGenerator.getInstance().reset();
    }

    public interface Interface {
        void reset();
    }
}
