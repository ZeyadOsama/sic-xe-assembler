package assembler.tables;

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
}