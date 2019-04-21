package assembler.tables;

import assembler.constants.Format;
import assembler.constants.OperandType;
import assembler.structure.Instruction;

import java.util.HashMap;

public final class OperationTable {

    private static OperationTable instance = new OperationTable();

    public static OperationTable getInstance() {
        return instance;
    }

    private OperationTable() {
    }

    private static HashMap<String, Instruction> operationTable = new HashMap<>();

    static {
        load();
    }

    public static boolean containsKey(String opcode) {
        return operationTable.containsKey(opcode);
    }

    public static Instruction getInstruction(String mnemonic) {
        return operationTable.get(mnemonic);
    }

    public static HashMap<String, Instruction> get() {
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

    private static void load() {
        operationTable.put("ADD",
                new Instruction("ADD", "18", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ADD",
                new Instruction("+ADD", "18", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ADDR",
                new Instruction("ADDR", "90", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("AND",
                new Instruction("AND", "40", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+AND",
                new Instruction("+AND", "40", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("CLEAR",
                new Instruction("CLEAR", "4", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("COMP",
                new Instruction("COMP", "28", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+COMP",
                new Instruction("+COMP", "28", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("COMPR",
                new Instruction("COMPR", "A0", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("DIV",
                new Instruction("DIV", "24", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+DIV",
                new Instruction("+DIV", "24", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("DIVR",
                new Instruction("DIVR", "9C", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("J",
                new Instruction("J", "3C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+J",
                new Instruction("+J", "3C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JEQ",
                new Instruction("JEQ", "30", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JEQ",
                new Instruction("+JEQ", "30", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JGT",
                new Instruction("JGT", "34", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JGT",
                new Instruction("+JGT", "34", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JLT",
                new Instruction("JLT", "38", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JLT",
                new Instruction("+JLT", "38", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("JSUB",
                new Instruction("JSUB", "48", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+JSUB",
                new Instruction("+JSUB", "48", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDA",
                new Instruction("LDA", "00", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDA",
                new Instruction("+LDA", "00", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDB",
                new Instruction("LDB", "68", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDB",
                new Instruction("+LDB", "68", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDCH",
                new Instruction("LDCH", "50", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDCH",
                new Instruction("+LDCH", "50", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDL",
                new Instruction("LDL", "08", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDL",
                new Instruction("+LDL", "08", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDS",
                new Instruction("LDS", "6C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDS",
                new Instruction("+LDS", "6C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDT",
                new Instruction("LDT", "74", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDT",
                new Instruction("+LDT", "74", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("LDX",
                new Instruction("LDX", "04", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+LDX",
                new Instruction("+LDX", "04", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MUL",
                new Instruction("MUL", "20", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+MUL",
                new Instruction("+MUL", "20", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("MULR",
                new Instruction("MULR", "98", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("OR",
                new Instruction("OR", "44", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+OR",
                new Instruction("+OR", "44", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RD",
                new Instruction("RD", "D8", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+RD",
                new Instruction("+RD", "D8", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("RMO",
                new Instruction("RMO", "AC", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("RSUB",
                new Instruction("RSUB", "4C", Format.THREE, OperandType.NONE, OperandType.NONE));
        operationTable.put("+RSUB",
                new Instruction("+RSUB", "4C", Format.FOUR, OperandType.NONE, OperandType.NONE));
        operationTable.put("SHIFTL",
                new Instruction("SHIFTL", "A4", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("SHIFTR",
                new Instruction("SHIFTR", "A8", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("STA",
                new Instruction("STA", "0C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STA",
                new Instruction("+STA", "0C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STB",
                new Instruction("STB", "78", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STB",
                new Instruction("+STB", "78", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STCH",
                new Instruction("STCH", "54", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STCH",
                new Instruction("+STCH", "54", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STL",
                new Instruction("STL", "14", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STL",
                new Instruction("+STL", "14", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STS",
                new Instruction("STS", "7C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STS",
                new Instruction("+STS", "7C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STT",
                new Instruction("STT", "84", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STT",
                new Instruction("+STT", "84", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("STX",
                new Instruction("STX", "10", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+STX",
                new Instruction("+STX", "10", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUB",
                new Instruction("SUB", "1C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+SUB",
                new Instruction("+SUB", "1C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("SUBR",
                new Instruction("SUBR", "94", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("TD",
                new Instruction("TD", "E0", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TD",
                new Instruction("+TD", "E0", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIX",
                new Instruction("TIX", "2C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+TIX",
                new Instruction("+TIX", "2C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("TIXR",
                new Instruction("TIXR", "B8", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("WD",
                new Instruction("WD", "DC", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+WD",
                new Instruction("+WD", "DC", Format.FOUR, OperandType.VALUE, OperandType.NONE));
    }
}
