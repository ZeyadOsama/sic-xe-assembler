package assembler.tables;

import assembler.core.ErrorHandler;
import assembler.core.LocationCounter;
import assembler.structure.Instruction;
import assembler.structure.Symbol;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public final class SymbolTable {

    /**
     * Singleton class
     */
    private static SymbolTable instance = new SymbolTable();

    public static SymbolTable getInstance() {
        return instance;
    }

    private SymbolTable() {
    }

    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();

    public HashMap<String, Symbol> get() {
        return symbolTable;
    }

    public Symbol getSymbol(@NotNull String label) {
        return symbolTable.get(label);
    }

    public boolean containsSymbol(@NotNull String label) {
        return symbolTable.containsKey(label.toUpperCase());
    }

    public void update(@NotNull Instruction instruction) {
        String label = instruction.getLabel();
        if (label != null && !instruction.hasError()) {
            if (symbolTable.containsKey(label)) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.DUPLICATE_LABEL);
                return;
            }
            symbolTable.put(label,
                    new Symbol(label, LocationCounter.getInstance().getCurrentAddress()));
        }
    }
}