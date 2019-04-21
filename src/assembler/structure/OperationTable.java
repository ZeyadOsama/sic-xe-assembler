package assembler.structure;

import assembler.constants.Format;
import assembler.constants.OperandType;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public final class OperationTable {

    private static final Set<String> assemblerDirectives = new HashSet<>();
    private static OperationTable instance = new OperationTable();

    public static OperationTable getInstance() {
        return instance;
    }

    private OperationTable() {
    }

    private static HashMap<String, Instruction> operationTable = new HashMap<>();

    static {
        loadTable();
        loadAssemblerDirectives();
    }

    public static Instruction getOperation(String operationName) {
        return operationTable.get(operationName);
    }

    public static HashMap<String, Instruction> get() {
        return operationTable;
    }

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOpcode(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return assemblerDirectives.contains(directive);
    }

    public static boolean isOpcode(String opcode) {
        return operationTable.containsKey(opcode);
    }

    public static String getOpcode(String mnemonic) {
        return operationTable.get(mnemonic.toLowerCase()).getOpcode();
    }

    public static Format getFormat(String mnemonic) {
        return operationTable.get(mnemonic.toLowerCase()).getFormat();
    }

    public static OperandType getFirstOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toLowerCase()).getFirstOperand();
    }

    public static OperandType getSecondOperandType(String mnemonic) {
        return operationTable.get(mnemonic.toLowerCase()).getSecondOperand();
    }

    private static void loadTable() {
        operationTable.put("add",
                new Instruction("add", "18", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+add",
                new Instruction("+add", "18", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("addr",
                new Instruction("addr", "90", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("and",
                new Instruction("and", "40", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+and",
                new Instruction("+and", "40", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("clear",
                new Instruction("clear", "4", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("comp",
                new Instruction("comp", "28", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+comp",
                new Instruction("+comp", "28", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("compr",
                new Instruction("compr", "A0", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("div",
                new Instruction("div", "24", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+div",
                new Instruction("+div", "24", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("divr",
                new Instruction("divr", "9C", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("j",
                new Instruction("j", "3C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+j",
                new Instruction("+j", "3C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("jeq",
                new Instruction("jeq", "30", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+jeq",
                new Instruction("+jeq", "30", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("jgt",
                new Instruction("jgt", "34", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+jgt",
                new Instruction("+jgt", "34", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("jlt",
                new Instruction("jlt", "38", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+jlt",
                new Instruction("+jlt", "38", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("jsub",
                new Instruction("jsub", "48", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+jsub",
                new Instruction("+jsub", "48", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("lda",
                new Instruction("lda", "00", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+lda",
                new Instruction("+lda", "00", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ldb",
                new Instruction("ldb", "68", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ldb",
                new Instruction("+ldb", "68", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ldch",
                new Instruction("ldch", "50", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ldch",
                new Instruction("+ldch", "50", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ldl",
                new Instruction("ldl", "08", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ldl",
                new Instruction("+ldl", "08", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("lds",
                new Instruction("lds", "6C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+lds",
                new Instruction("+lds", "6C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ldt",
                new Instruction("ldt", "74", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ldt",
                new Instruction("+ldt", "74", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("ldx",
                new Instruction("ldx", "04", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+ldx",
                new Instruction("+ldx", "04", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("mul",
                new Instruction("mul", "20", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+mul",
                new Instruction("+mul", "20", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("mulr",
                new Instruction("mulr", "98", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("or",
                new Instruction("or", "44", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+or",
                new Instruction("+or", "44", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("rd",
                new Instruction("rd", "D8", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+rd",
                new Instruction("+rd", "D8", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("rmo",
                new Instruction("rmo", "AC", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("rsub",
                new Instruction("rsub", "4C", Format.THREE, OperandType.NONE, OperandType.NONE));
        operationTable.put("+rsub",
                new Instruction("+rsub", "4C", Format.FOUR, OperandType.NONE, OperandType.NONE));
        operationTable.put("shiftl",
                new Instruction("shiftl", "A4", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("shiftr",
                new Instruction("shiftr", "A8", Format.TWO, OperandType.REGISTER, OperandType.VALUE));
        operationTable.put("sta",
                new Instruction("sta", "0C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+sta",
                new Instruction("+sta", "0C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("stb",
                new Instruction("stb", "78", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+stb",
                new Instruction("+stb", "78", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("stch",
                new Instruction("stch", "54", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+stch",
                new Instruction("+stch", "54", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("stl",
                new Instruction("stl", "14", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+stl",
                new Instruction("+stl", "14", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("sts",
                new Instruction("sts", "7C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+sts",
                new Instruction("+sts", "7C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("stt",
                new Instruction("stt", "84", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+stt",
                new Instruction("+stt", "84", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("stx",
                new Instruction("stx", "10", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+stx",
                new Instruction("+stx", "10", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("sub",
                new Instruction("sub", "1C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+sub",
                new Instruction("+sub", "1C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("subr",
                new Instruction("subr", "94", Format.TWO, OperandType.REGISTER, OperandType.REGISTER));
        operationTable.put("td",
                new Instruction("td", "E0", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+td",
                new Instruction("+td", "E0", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("tix",
                new Instruction("tix", "2C", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+tix",
                new Instruction("+tix", "2C", Format.FOUR, OperandType.VALUE, OperandType.NONE));
        operationTable.put("tixr",
                new Instruction("tixr", "B8", Format.TWO, OperandType.REGISTER, OperandType.NONE));
        operationTable.put("wd",
                new Instruction("wd", "DC", Format.THREE, OperandType.VALUE, OperandType.NONE));
        operationTable.put("+wd",
                new Instruction("+wd", "DC", Format.FOUR, OperandType.VALUE, OperandType.NONE));
    }

    private static void loadAssemblerDirectives() {
        assemblerDirectives.add("BYTE");
        assemblerDirectives.add("RESB");
        assemblerDirectives.add("WORD");
        assemblerDirectives.add("RESW");
        assemblerDirectives.add("START");
        assemblerDirectives.add("BASE");
        assemblerDirectives.add("NOBASE");
        assemblerDirectives.add("END");
        assemblerDirectives.add("LTORG");
        assemblerDirectives.add("EXTREF");
        assemblerDirectives.add("EXTDEF");
        assemblerDirectives.add("ORG");
        assemblerDirectives.add("EQU");
        assemblerDirectives.add("CSECT");
    }
}
