package assembler.structure;

import java.util.HashMap;

public class SymbolTable {

    private static SymbolTable instance = new SymbolTable();
    private HashMap<String, Symbol> symbolTable = new HashMap<>();

    private SymbolTable() {
    }

    public static SymbolTable getInstance() {
        return instance;
    }

    public Symbol getSymbol(String label) {
        return symbolTable.get(label);
    }

    public void addToTable(String label, Symbol symbol) {
        symbolTable.put(label, symbol);
    }

    public void addToTable(String address, String label, int length, boolean relocatable) {
        symbolTable.put(label, new Symbol(address, label, length, relocatable));
    }
}