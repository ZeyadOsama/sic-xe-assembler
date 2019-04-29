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
import static misc.utils.Validations.Operand.*;
import static misc.utils.Validations.isOperation;

public class ObjectCodeGenerator {

    private static Record headerRecord;
    private static Record textRecord;
    private static Record endRecord;
    private int startAddress;
    private int endAddress;
    private ArrayList<String> objectCode;
    private ArrayList<Instruction> instructions;

    private SymbolTable symbolTable = SymbolTable.getInstance();

    public ObjectCodeGenerator() {
        objectCode = new ArrayList<>();
        headerRecord = new Record(Record.HEADER);
        textRecord = new Record(Record.TEXT);
        endRecord = new Record(Record.END);
        instructions = Parser.getInstance().getParsedInstructions();
    }

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

    private ArrayList<String> generateTextRecord() {
        for (Instruction instruction : instructions) {
            String objectCode = null;
            if (isOperation(instruction.getMnemonic())) {
                switch (OperationTable.getOperation(instruction.getMnemonic()).getFormat()) {
                    case ONE:
                        objectCode = generateFormatOne(instruction);
                        break;
                    case TWO:
                        objectCode = generateFormatTwo(instruction);
                        break;
                    case THREE:
                        objectCode = generateFormatThree(instruction);
                        break;
                    case FOUR:
                        objectCode = generateFormatFour(instruction);
                        break;
                }
            } else if (instruction.getMnemonic().equals(DirectiveTable.BYTE) && instruction.hasFirstOperand())
                objectCode = extendLength(Decimal.toHexadecimal(instruction.getFirstOperand()), 2);
            else if (instruction.getMnemonic().equals(DirectiveTable.WORD) && instruction.hasFirstOperand())
                objectCode = extendLength(Decimal.toHexadecimal(instruction.getFirstOperand()), 6);

            textRecord.addContent(objectCode);
        }
        System.out.println(textRecord.content);
        return null;
    }

    private void generateEndRecord() {

    }

    private String generateFormatOne(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        return extendLength(Decimal.toBinary(opcode), 8);
    }

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
            if (isDirect(instruction)) {
                i = '1';
                n = '1';
            } else if (isImmediate(instruction)) {
                i = '1';
                n = '0';
            } else {
                i = '0';
                n = '1';
            }
        }
        ;
        return new StringBuilder().append(n).append(i).append(x).toString();
    }

    private String getBPE(Instruction instruction) {
        // TODO: p bit not set right now
        char b, p, e;
        if (OperationTable.getOperation(instruction.getMnemonic()).getFormat() == Format.FOUR) {
            b = '0';
            p = '0';
            e = '1';
        } else {
            e = '1';
            if (Program.hasBaseDirective()) {
                b = '1';
                p = '0';
            } else {
                b = '0';
                p = '1';
            }
        }
        return new StringBuilder().append(b).append(p).append(e).toString();
    }


    private String getImmediateValue(String operand) {
        return operand.replace("#", "");
    }

    private String getIndirectValue(String operand) {
        return operand.replace("@", "");
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

        private void addContent(String record) {
            if (record == null) return;

            String string;
            if (content.length() > lines * 68) {
                lines++;
                string = "\n" + TEXT + SEPARATOR + record;
            } else string = SEPARATOR + record;
            content.append(string);
        }
    }


//        public void sic(){
//            if (isOperation(instruction.getMnemonic())) {
//                opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
//                index = instruction.isIndexed() ? "1" : "0";
//
//                if (instruction.hasFirstOperand()) {
//                    String temp;
//                    if (instruction.getFirstOperand().startsWith("#"))
//                        temp = instruction.getFirstOperand().replace("#", "");
//                    else temp = instruction.getFirstOperand();
//
//                    if (symbolTable.containsSymbol(temp))
//                        firstOperand = String.valueOf(symbolTable.getSymbol(temp).getAddress());
//                    else if (RegisterTable.contains(temp))
//                        firstOperand = String.valueOf(RegisterTable.getRegister(temp).getAddress());
//                    else
//                        firstOperand = temp;
//                }
//
//                if (instruction.hasSecondOperand()) {
//                    String temp;
//                    if (instruction.getSecondOperand().startsWith("#"))
//                        temp = instruction.getSecondOperand().replace("#", "");
//                    else temp = instruction.getSecondOperand();
//
//                    if (symbolTable.containsSymbol(temp)) {
//                        secondOperand = String.valueOf(symbolTable.getSymbol(temp).getAddress());
//                    } else if (RegisterTable.contains(temp)) {
//                        secondOperand = String.valueOf(RegisterTable.getRegister(temp).getAddress());
//                    } else {
//                        secondOperand = temp;
//                    }
//                }
//            } else if (isDirective(instruction.getMnemonic()) && !isReservationDirective(instruction.getMnemonic()) && instruction.hasFirstOperand()) {
//                firstOperand = instruction.getFirstOperand();
//            }
//
//
//            String objectCode;
//            if (instruction.getMnemonic().equals(DirectiveTable.START))
//                objectCode = extendLength(firstOperand, 6);
//            else {
//                if (opcode != null)
//                    opcode = extendLength(Decimal.toBinary(opcode), 8);
//                else opcode = extendLength(Decimal.toBinary(0), 8);
//
//                if (index != null)
//                    index = Decimal.toBinary(index);
//                else index = "0";
//
//                if (firstOperand != null)
//                    firstOperand = extendLength(Decimal.toBinary(firstOperand), 15);
//                else firstOperand = extendLength(Decimal.toBinary("0"), 15);
//
//                objectCode = extendLength(Binary.toHexadecimal(opcode + index + firstOperand), 6);
//            }
//            System.out.println(objectCode);
//            textRecord.addContent(objectCode);
//        }
}
