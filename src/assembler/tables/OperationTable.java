package assembler.tables;

import assembler.constants.Format;
import assembler.constants.OperandType;
import assembler.structure.Operation;

import java.util.HashMap;

public final class OperationTable {

    static {
        load();
    }

    private static OperationTable instance = new OperationTable();

    public static OperationTable getInstance() {
        return instance;
    }

    private OperationTable() {
    }

    private static HashMap<String, Operation> operationTable = new HashMap<>();

    public static boolean containsKey(String opcode) {
        return operationTable.containsKey(opcode);
    }

    public static Operation getInstruction(String mnemonic) {
        return operationTable.get(mnemonic);
    }

    public static HashMap<String, Operation> get() {
        return operationTable;
    }

    public static String getOpcode(String mnemonic) {
        return operationTable.get(mnemonic.toLowerCase()).getOpcode();
    }

    public static Format getFormat(String mnemonic) {
        return operationTable.get(mnemonic.toUpperCase()).getFormat();
    }

    public static OperandType getFirstOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toUpperCase()).getFirstOperand();
    }

    public static OperandType getSecondOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toUpperCase()).getSecondOperand();
    }

    public static Boolean hasFirstOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toUpperCase()).hasFirstOperand();
    }

    public static Boolean hasSecondOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toUpperCase()).hasSecondOperand();
    }

    private static void load() {
        operationTable.put("ADD",
                new Operation("ADD", "18", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ADD",
                new Operation("+ADD", "18", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ADDR",
                new Operation("ADDR", "90", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("AND",
                new Operation("AND", "40", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+AND",
                new Operation("+AND", "40", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("CLEAR",
                new Operation("CLEAR", "4", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("COMP",
                new Operation("COMP", "28", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+COMP",
                new Operation("+COMP", "28", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("COMPR",
                new Operation("COMPR", "A0", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("DIV",
                new Operation("DIV", "24", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+DIV",
                new Operation("+DIV", "24", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("DIVR",
                new Operation("DIVR", "9C", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("J",
                new Operation("J", "3C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+J",
                new Operation("+J", "3C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JEQ",
                new Operation("JEQ", "30", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JEQ",
                new Operation("+JEQ", "30", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JGT",
                new Operation("JGT", "34", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JGT",
                new Operation("+JGT", "34", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JLT",
                new Operation("JLT", "38", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JLT",
                new Operation("+JLT", "38", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JSUB",
                new Operation("JSUB", "48", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JSUB",
                new Operation("+JSUB", "48", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDA",
                new Operation("LDA", "00", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDA",
                new Operation("+LDA", "00", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDB",
                new Operation("LDB", "68", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDB",
                new Operation("+LDB", "68", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDCH",
                new Operation("LDCH", "50", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDCH",
                new Operation("+LDCH", "50", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDL",
                new Operation("LDL", "08", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDL",
                new Operation("+LDL", "08", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDS",
                new Operation("LDS", "6C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDS",
                new Operation("+LDS", "6C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDT",
                new Operation("LDT", "74", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDT",
                new Operation("+LDT", "74", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDX",
                new Operation("LDX", "04", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDX",
                new Operation("+LDX", "04", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MUL",
                new Operation("MUL", "20", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+MUL",
                new Operation("+MUL", "20", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MULR",
                new Operation("MULR", "98", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("OR",
                new Operation("OR", "44", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+OR",
                new Operation("+OR", "44", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RD",
                new Operation("RD", "D8", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+RD",
                new Operation("+RD", "D8", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RMO",
                new Operation("RMO", "AC", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("RSUB",
                new Operation("RSUB", "4C", Format.THREE, OperandType.NONE, OperandType.NONE));
        operationTable.put("+RSUB",
                new Operation("+RSUB", "4C", Format.FOUR, OperandType.NONE, OperandType.NONE));
        operationTable.put("SHIFTL",
                new Operation("SHIFTL", "A4", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("SHIFTR",
                new Operation("SHIFTR", "A8", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("STA",
                new Operation("STA", "0C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STA",
                new Operation("+STA", "0C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STB",
                new Operation("STB", "78", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STB",
                new Operation("+STB", "78", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STCH",
                new Operation("STCH", "54", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STCH",
                new Operation("+STCH", "54", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STL",
                new Operation("STL", "14", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STL",
                new Operation("+STL", "14", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STS",
                new Operation("STS", "7C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STS",
                new Operation("+STS", "7C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STT",
                new Operation("STT", "84", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STT",
                new Operation("+STT", "84", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STX",
                new Operation("STX", "10", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STX",
                new Operation("+STX", "10", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUB",
                new Operation("SUB", "1C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+SUB",
                new Operation("+SUB", "1C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUBR",
                new Operation("SUBR", "94", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("TD",
                new Operation("TD", "E0", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TD",
                new Operation("+TD", "E0", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIX",
                new Operation("TIX", "2C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TIX",
                new Operation("+TIX", "2C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIXR",
                new Operation("TIXR", "B8", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("WD",
                new Operation("WD", "DC", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+WD",
                new Operation("+WD", "DC", Format.FOUR, OperandType.VALUE, OperandType.NONE));
    }
}
