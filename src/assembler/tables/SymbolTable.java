package assembler.tables;

import assembler.core.Assembler;
import assembler.core.ErrorHandler;
import assembler.core.LocationCounter;
import assembler.structure.Instruction;
import assembler.structure.Symbol;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public final class SymbolTable implements Assembler.Interface {

    /**
     * Singleton class
     */
    private static SymbolTable instance = new SymbolTable();

    public static SymbolTable getInstance() {
        return instance;
    }

    private SymbolTable() {
    }

    private ArrayList<Symbol> symbolList = new ArrayList<>();
    private HashMap<String, Symbol> symbolTable = new HashMap<>();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();

    @Override
    public void reset() {
        symbolList.clear();
        symbolTable.clear();
    }

    public HashMap<String, Symbol> get() {
        return symbolTable;
    }

    public Symbol getSymbol(@NotNull String label) {
        return symbolTable.get(label);
    }

    public ArrayList<Symbol> getSymbolList() {
        return symbolList;
    }

    public boolean containsSymbol(String label) {
        return label != null && symbolTable.containsKey(label.toUpperCase());
    }

    public void update(@NotNull Instruction instruction) {
        String label = instruction.getLabel();
        if (label != null && !instruction.hasError()) {
            if (symbolTable.containsKey(label)) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.DUPLICATE_LABEL);
                return;
            }
            Symbol symbol = new Symbol(label, LocationCounter.getInstance().getCurrentAddress(), instruction.getFirstOperand());
            symbolTable.put(label, symbol);
            symbolList.add(symbol);
        }
    }
}