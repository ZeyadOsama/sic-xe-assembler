package assembler.core;

import assembler.constants.Format;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import assembler.tables.RegisterTable;
import assembler.tables.SymbolTable;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Converter.Binary;
import static misc.utils.Converter.Decimal;
import static misc.utils.Utils.extendLength;
import static misc.utils.Utils.parseDataOperand;
import static misc.utils.Validations.Operand.*;
import static misc.utils.Validations.isManipulativeOperation;
import static misc.utils.Validations.isOperation;

public class ObjectCodeGenerator {

    private static Record headerRecord;
    private static Record textRecord;
    private static Record endRecord;
    private ArrayList<String> objectCode;
    public Terminal terminal = new Terminal();

    private SymbolTable symbolTable = SymbolTable.getInstance();
    private ArrayList<Instruction> parsedInstructions;

    public void generate() {
        generateHeaderRecord();
        generateTextRecord();
        generateEndRecord();
    }

    private void generateHeaderRecord() {
        headerRecord.addContent(Program.getName());
        headerRecord.addContent(Program.getStartAddress());
        headerRecord.addContent(Program.getObjectCodeLength());
        System.out.println(headerRecord.content);
    }

    public ObjectCodeGenerator() {
        objectCode = new ArrayList<>();
        headerRecord = new Record(Record.HEADER);
        textRecord = new Record(Record.TEXT);
        endRecord = new Record(Record.END);
        parsedInstructions = Parser.getInstance().getParsedInstructions();
    }

    private void generateEndRecord() {

    }

    private ArrayList<String> generateTextRecord() {
        Record record = new Record();
        int recordLength = 0;

        for (Instruction instruction : parsedInstructions) {
            String objectCode = null;
            if (isOperation(instruction.getMnemonic())) {
                switch (OperationTable.getOperation(instruction.getMnemonic()).getFormat()) {
                    case ONE:
                        objectCode = generateFormatOne(instruction);
                        recordLength += 1;
                        break;
                    case TWO:
                        objectCode = generateFormatTwo(instruction);
                        recordLength += 2;
                        break;
                    case THREE:
                        objectCode = generateFormatThree(instruction);
                        recordLength += 3;
                        break;
                    case FOUR:
                        objectCode = generateFormatFour(instruction);
                        recordLength += 4;
                        break;
                }
            } else if (instruction.getMnemonic().equals(DirectiveTable.BYTE) && instruction.hasFirstOperand()) {
                String operand = instruction.getFirstOperand();
                if (isLiteral(operand))
                    operand = parseDataOperand(operand);
                objectCode = extendLength(Decimal.toHexadecimal(operand), 2);
            } else if (instruction.getMnemonic().equals(DirectiveTable.WORD) && instruction.hasFirstOperand()) {
                String operand = instruction.getFirstOperand();
                if (isLiteral(operand))
                    operand = parseDataOperand(operand);
                objectCode = extendLength(Decimal.toHexadecimal(operand), 6);
            }

            textRecord.addContent(objectCode);
        }
        System.out.println(textRecord.content);
        return null;
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format one
     */
    private String generateFormatOne(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        return extendLength(Decimal.toBinary(opcode), 8);
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format two
     */
    private String generateFormatTwo(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        opcode = extendLength(Decimal.toBinary(opcode), 8);

        String firstOperand = String.valueOf(RegisterTable.getRegister(instruction.getFirstOperand()).getAddress());
        firstOperand = extendLength(Decimal.toBinary(firstOperand), 4);

        String secondOperand;
        if (instruction.hasSecondOperand()) {
            secondOperand = String.valueOf(RegisterTable.getRegister(instruction.getSecondOperand()).getAddress());
            secondOperand = extendLength(Decimal.toBinary(secondOperand), 4);
        } else secondOperand = extendLength("0", 4);

        return extendLength(Binary.toHexadecimal(opcode + firstOperand + secondOperand), 4);
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format three
     */
    private String generateFormatThree(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        opcode = extendLength(Decimal.toBinary(opcode), 6);

        String operand = instruction.getFirstOperand();
        if (isIndirect(operand))
            operand = getIndirectValue(operand);
        else if (isImmediate(operand))
            operand = getImmediateValue(operand);
        if (symbolTable.containsSymbol(operand))
            operand = String.valueOf(symbolTable.getSymbol(operand).getAddress());
        operand = extendLength(Decimal.toBinary(operand), 12);

        return extendLength(Binary.toHexadecimal(opcode
                + getNIX(instruction) + getBPE(instruction)
                + operand), 6);
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format four
     */
    private String generateFormatFour(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        opcode = extendLength(Decimal.toBinary(opcode), 6);

        String operand = instruction.getFirstOperand();
        if (isIndirect(operand))
            operand = getIndirectValue(operand);
        else if (isImmediate(operand))
            operand = getImmediateValue(operand);
        if (symbolTable.containsSymbol(operand))
            operand = String.valueOf(symbolTable.getSymbol(operand).getAddress());
        operand = extendLength(Decimal.toBinary(operand), 20);

        return extendLength(Binary.toHexadecimal(opcode
                + getNIX(instruction) + getBPE(instruction)
                + operand), 8);
    }

    private String getNIX(Instruction instruction) {
        char n, i, x;
        if (instruction.isIndexed()) {
            x = '1';
            i = '1';
            n = '1';
        } else {
            x = '0';
            if (isIndirect(instruction)) {
                i = '0';
                n = '1';
            } else if (isImmediate(instruction)) {
                i = '1';
                n = '0';
            } else {
                i = '1';
                n = '1';
            }
        }
        ;
        return new StringBuilder().append(n).append(i).append(x).toString();
    }


    private String getImmediateValue(String operand) {
        return operand.replace("#", "");
    }

    private String getIndirectValue(String operand) {
        return operand.replace("@", "");
    }

    private String getBPE(Instruction instruction) {
        char b, p, e;
        if (OperationTable.getOperation(instruction.getMnemonic()).getFormat() == Format.FOUR) {
            b = '0';
            p = '0';
            e = '1';
        } else {
            if (Program.hasBaseDirective()) {
                b = '1';
                p = '0';
            } else if (isManipulativeOperation(instruction.getMnemonic())) {
                b = '0';
                p = '1';
            } else {
                b = '0';
                p = '0';
            }
            e = '0';
        }
        return new StringBuilder().append(b).append(p).append(e).toString();
    }

    public class Terminal {

        private Terminal() {
        }

        public void show() {
            System.out.println(headerRecord);
            System.out.println(textRecord);
            System.out.println(endRecord);
        }
    }

    private class Record {
        private static final char SEPARATOR = '^';
        private static final String HEADER = "H";
        private static final String TEXT = "T";
        private static final String END = "E";

        private StringBuilder content;
        private int lines = 1;

        private Record(String recordTitle) {
            content = new StringBuilder();
            content.append(recordTitle);
        }

        private Record() {
            content = new StringBuilder();
        }

        private void addContent(String record) {
            if (record == null) return;

            String string;
            if (content.length() > lines * 68) {
                lines++;
                string = "\n" + TEXT + SEPARATOR + record;
            } else string = SEPARATOR + record;
            content.append(string);
        }

        private int getLength() {
            return content.length();
        }
    }
}
