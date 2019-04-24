package assembler.tables;

import assembler.core.LocationCounter;
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
        if (instruction.getLabel() != null) {
            Symbol symbol = new Symbol();
            symbol.setAddress(LocationCounter.getInstance().getLastAddress());
            symbol.setLabel(instruction.getLabel());
            if (instruction.hasFirstOperand()) symbol.setValue(instruction.getFirstOperand());
            symbolTable.put(instruction.getLabel(), symbol);
        }
    }
}