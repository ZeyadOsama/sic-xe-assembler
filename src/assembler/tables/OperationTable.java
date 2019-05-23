package assembler.tables;

import assembler.constants.Format;
import assembler.constants.OperandType;
import assembler.structure.Operation;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;

public final class OperationTable {

    private OperationTable() {
    }

    private static HashMap<String, Operation> operationTable = new HashMap<>();
    private static HashSet<String> manipulativeOperations = new HashSet<>();

    public static HashMap<String, Operation> get() {
        return operationTable;
    }

    public static HashSet<String> getManipulativeOperations() {
        return manipulativeOperations;
    }

    public static Operation getOperation(@NotNull String mnemonic) {
        return operationTable.get(mnemonic);
    }

    public static boolean containsMnemonic(@NotNull String mnemonic) {
        return operationTable.containsKey(mnemonic.toUpperCase());
    }

    /**
     * Load all mnemonics details to op-table.
     * Adds mnemonics in the format of Operation class.
     *
     * @see Operation class
     */
    private static void load() {
        operationTable.put("ADD",
                new Operation("ADD", 0x18, Format.THREE, OperandType.VALUE, OperandType.DONT_CARE));
        operationTable.put("+ADD",
                new Operation("+ADD", 0x18, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ADDR",
                new Operation("ADDR", 0x90, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("AND",
                new Operation("AND", 0x40, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+AND",
                new Operation("+AND", 0x40, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("CLEAR",
                new Operation("CLEAR", 0xB4, Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("COMP",
                new Operation("COMP", 0x28, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+COMP",
                new Operation("+COMP", 0x28, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("COMPR",
                new Operation("COMPR", 0xA0, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("DIV",
                new Operation("DIV", 0x24, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+DIV",
                new Operation("+DIV", 0x24, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("DIVR",
                new Operation("DIVR", 0x9C, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("J",
                new Operation("J", 0x3C, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+J",
                new Operation("+J", 0x3C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JEQ",
                new Operation("JEQ", 0x30, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JEQ",
                new Operation("+JEQ", 0x30, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JGT",
                new Operation("JGT", 0x34, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JGT",
                new Operation("+JGT", 0x34, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JLT",
                new Operation("JLT", 0x38, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JLT",
                new Operation("+JLT", 0x38, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JSUB",
                new Operation("JSUB", 0x48, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JSUB",
                new Operation("+JSUB", 0x48, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDA",
                new Operation("LDA", 0x00, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDA",
                new Operation("+LDA", 0x00, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDB",
                new Operation("LDB", 0x68, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDB",
                new Operation("+LDB", 0x68, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDCH",
                new Operation("LDCH", 0x50, Format.THREE, OperandType.VALUE, OperandType.DONT_CARE));
        operationTable.put("+LDCH",
                new Operation("+LDCH", 0x50, Format.FOUR, OperandType.VALUE, OperandType.DONT_CARE));
        operationTable.put("LDL",
                new Operation("LDL", 0x08, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDL",
                new Operation("+LDL", 0x08, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDS",
                new Operation("LDS", 0x6C, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDS",
                new Operation("+LDS", 0x6C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDT",
                new Operation("LDT", 0x74, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDT",
                new Operation("+LDT", 0x74, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDX",
                new Operation("LDX", 0x04, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDX",
                new Operation("+LDX", 0x04, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MUL",
                new Operation("MUL", 0x20, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+MUL",
                new Operation("+MUL", 0x20, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MULR",
                new Operation("MULR", 0x98, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("OR",
                new Operation("OR", 0x44, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+OR",
                new Operation("+OR", 0x44, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RD",
                new Operation("RD", 0xD8, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+RD",
                new Operation("+RD", 0xD8, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RMO",
                new Operation("RMO", 0xAC, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("RSUB",
                new Operation("RSUB", 0x4C, Format.THREE, OperandType.NONE, OperandType.NONE));
        operationTable.put("+RSUB",
                new Operation("+RSUB", 0x4C, Format.FOUR, OperandType.NONE, OperandType.NONE));
        operationTable.put("SHIFTL",
                new Operation("SHIFTL", 0xA4, Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("SHIFTR",
                new Operation("SHIFTR", 0xA8, Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("STA",
                new Operation("STA", 0x0C, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STA",
                new Operation("+STA", 0x0C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STB",
                new Operation("STB", 0x78, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STB",
                new Operation("+STB", 0x78, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STCH",
                new Operation("STCH", 0x54, Format.THREE, OperandType.VALUE, OperandType.DONT_CARE));
        operationTable.put("+STCH",
                new Operation("+STCH", 0x54, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STL",
                new Operation("STL", 0x14, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STL",
                new Operation("+STL", 0x14, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STS",
                new Operation("STS", 0x7C, Format.THREE, OperandType.VALUE, OperandType.DONT_CARE));
        operationTable.put("+STS",
                new Operation("+STS", 0x7C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STT",
                new Operation("STT", 0x84, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STT",
                new Operation("+STT", 0x84, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STX",
                new Operation("STX", 0x10, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STX",
                new Operation("+STX", 0x10, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUB",
                new Operation("SUB", 0x1C, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+SUB",
                new Operation("+SUB", 0x1C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUBR",
                new Operation("SUBR", 0x94, Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("TD",
                new Operation("TD", 0xE0, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TD",
                new Operation("+TD", 0xE0, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIX",
                new Operation("TIX", 0x2C, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TIX",
                new Operation("+TIX", 0x2C, Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIXR",
                new Operation("TIXR", 0xB8, Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("WD",
                new Operation("WD", 0xDC, Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+WD",
                new Operation("+WD", 0xDC, Format.FOUR, OperandType.VALUE, OperandType.NONE));
    }

    /**
     * Load all mnemonics of operations that may be manipulative to
     * program counter.
     */
    private static void loadManipulativeOperations() {
        manipulativeOperations.add("J");
        manipulativeOperations.add("+J");
        manipulativeOperations.add("JLT");
        manipulativeOperations.add("+JLT");
        manipulativeOperations.add("JGT");
        manipulativeOperations.add("+JGT");
        manipulativeOperations.add("JEQ");
        manipulativeOperations.add("+JEQ");
        manipulativeOperations.add("JSUB");
        manipulativeOperations.add("+JSUB");
        manipulativeOperations.add("RSUB");
        manipulativeOperations.add("+RSUB");
    }

    /**
     * Cache op-table before starting program
     */
    static {
        load();
        loadManipulativeOperations();
    }
}
