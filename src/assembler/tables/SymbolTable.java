package assembler.tables;

import assembler.structure.Symbol;

import java.util.HashMap;

public class SymbolTable {

    private static SymbolTable instance = new SymbolTable();

    public static SymbolTable getInstance() {
        return instance;
    }

    private HashMap<String, Symbol> symbolTable = new HashMap<>();

    private SymbolTable() {
    }

    public HashMap<String, Symbol> get() {
        return symbolTable;
    }

    public Symbol getSymbol(String label) {
        return symbolTable.get(label);
    }

    public void add(String label, Symbol symbol) {
        symbolTable.put(label, symbol);
    }

    public void add(String address, String label, int length, boolean relocatable) {
        symbolTable.put(label, new Symbol(address, label, length, relocatable));
    }
}