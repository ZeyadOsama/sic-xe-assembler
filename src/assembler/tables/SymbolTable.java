package assembler.tables;

import assembler.core.LocationCounter;
import assembler.structure.ErrorHandler;
import assembler.structure.Instruction;
import assembler.structure.Symbol;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class SymbolTable {

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
        return symbolTable.containsKey(label);
    }

    public void put(@NotNull String label, Symbol symbol) {
        symbolTable.put(label, symbol);
    }

    public void update(@NotNull Instruction instruction) {
        String label = instruction.getLabel();
        if (label != null) {
            if (symbolTable.containsKey(label)) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.DUPLICATE_LABEL);
                return;
            }
            Symbol symbol = new Symbol();
            symbol.setAddress(LocationCounter.getInstance().getCurrentAddress());
            symbol.setLabel(instruction.getLabel());
            if (instruction.hasFirstOperand()) symbol.setValue(instruction.getFirstOperand());
            symbolTable.put(label, symbol);
        }
        errorHandler.setHasError(false);
    }
}