package assembler.core;

import assembler.constants.Format;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import assembler.tables.RegisterTable;
import assembler.tables.SymbolTable;
import misc.utils.ConsoleColors;
import misc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Converter.Binary;
import static misc.utils.Converter.Decimal;
import static misc.utils.Utils.extendLength;
import static misc.utils.Utils.parseDataOperand;
import static misc.utils.Validations.Operand.*;
import static misc.utils.Validations.isNumeric;
import static misc.utils.Validations.isOperation;

public final class ObjectCodeGenerator {

    private static ArrayList<String> records;
    private Record headerRecord;
    private Record textRecord;
    private Record endRecord;

    private SymbolTable symbolTable = SymbolTable.getInstance();
    private ArrayList<Instruction> parsedInstructions;
    private ArrayList<Integer> addresses;
    private int PC;
    private int displacement;

    public ObjectCodeGenerator() {
        headerRecord = new Record(Record.HEADER);
        textRecord = new Record(Record.TEXT);
        endRecord = new Record(Record.END);
        parsedInstructions = Parser.getInstance().getParsedInstructions();
        records = new ArrayList<>();
        addresses = LocationCounter.getInstance().getAddresses();
    }

    public void generate() {
        if (Program.hasError()) {
            ErrorHandler.out.println("Can not generate object code due to parsing errors.");
            return;
        }
        generateHeaderRecord();
        generateTextRecord();
        generateEndRecord();
    }

    private void generateHeaderRecord() {
        headerRecord.addContent(Program.getName());
        headerRecord.addContent(Program.getStartAddress());
        headerRecord.addContent(Program.getObjectCodeLength());
    }

    public static ArrayList<String> getRecords() {
        return records;
    }

    private void generateEndRecord() {
        endRecord.addContent(Program.getFirstExecutableInstructionAddress());
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format one
     */
    private String generateFormatOne(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        return extendLength(Decimal.toBinary(opcode), 8);
    }

    private void generateTextRecord() {
        ArrayList<String> objectCodes = new ArrayList<>();
        int startAddress = 0, recordLength = 0, i = 0;
        for (Instruction instruction : parsedInstructions) {
            if (recordLength == 0)
                startAddress = instruction.getAddress();
            if (i < addresses.size() - 1)
                PC = addresses.get(++i);
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
            recordLength += objectCode != null ? objectCode.length() / 2 : 0;
            if (textRecord.isLengthExceeding()) {
                recordLength = 0;
                startAddress = instruction.getAddress();
                textRecord.addContent(extendLength(Decimal.toHexadecimal(startAddress), 6));
                textRecord.addContent(extendLength(Decimal.toHexadecimal(recordLength), 2));
                for (String oc : objectCodes)
                    textRecord.addContent(oc);
                objectCodes.clear();
            }
            objectCodes.add(objectCode);
        }
        textRecord.addContent(extendLength(Decimal.toHexadecimal(startAddress), 6));
        textRecord.addContent(extendLength(Decimal.toHexadecimal(recordLength), 2));
        for (String oc : objectCodes)
            textRecord.addContent(oc);
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
        opcode = extendLength(removeZeros(Decimal.toBinary(opcode)), 6);
        return extendLength(Binary.toHexadecimal(opcode
                + getNIX(instruction) + getBPE(instruction)
                + extendLength(Decimal.toBinary(displacement), 12)), 6);
    }

    /**
     * @param instruction parsed
     * @return object code for a single instruction of format four
     */
    private String generateFormatFour(Instruction instruction) {
        String opcode = String.valueOf(OperationTable.getOperation(instruction.getMnemonic()).getOpcode());
        opcode = extendLength(removeZeros(Decimal.toBinary(opcode)), 6);

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

    /**
     * @param instruction to be calculated
     * @return n i x bits
     */
    private String getNIX(Instruction instruction) {
        char n, i, x;
        if (instruction.isIndexed()) {
            x = '1';
            i = '1';
            n = '1';
        } else {
            if (isIndirect(instruction)) {
                n = '1';
                i = '0';
            } else if (isImmediate(instruction)) {
                n = '0';
                i = '1';
            } else {
                n = '1';
                i = '1';
            }
            x = '0';
        }
        return String.valueOf(n) + i + x;
    }

    /**
     * @param instruction to be calculated
     * @return b p e bits
     */
    private String getBPE(Instruction instruction) {
        char b, p, e;
        if (OperationTable.getOperation(instruction.getMnemonic()).getFormat() == Format.FOUR) {
            b = '0';
            p = '0';
            e = '1';
            return String.valueOf(b) + p + e;
        } else {
            String operand = instruction.getFirstOperand();
            if (isIndirect(operand))
                operand = getIndirectValue(operand);
            else if (isImmediate(operand))
                operand = getImmediateValue(operand);

            e = '0';
            if (!symbolTable.containsSymbol(operand)) {
                operand = Utils.removeSpecialSymbol(operand);
                if (isNumeric(operand)) {
                    b = '0';
                    p = '0';
                    displacement = Integer.parseInt(operand);
                    return String.valueOf(b) + p + e;
                }
            } else {
                int targetAddress = symbolTable.getSymbol(operand).getAddress();

                displacement = targetAddress - PC;
                if (isPCRelative(displacement)) {
                    b = '0';
                    p = '1';
                    return String.valueOf(b) + p + e;
                }

                displacement = targetAddress - Program.getBaseRegisterValue();
                if (isBaseRelative(targetAddress)) {
                    b = '1';
                    p = '0';
                    return String.valueOf(b) + p + e;
                }
            }
        }
        displacement = 0;
        return null;
    }

    /**
     * @param operand to be parsed
     * @return operand value without any special symbols
     */
    @NotNull
    private String getImmediateValue(@NotNull String operand) {
        return operand.replace("#", "");
    }

    /**
     * @param operand to be parsed
     * @return operand value without any special symbols
     */
    @NotNull
    private String getIndirectValue(@NotNull String operand) {
        return operand.replace("@", "");
    }

    /**
     * SIC/XE opcode for format 3 and 4 gets rid of last two zeros in them.
     *
     * @param string which is the opcode
     * @return string without last two chars
     */
    @NotNull
    private String removeZeros(@NotNull String string) {
        return string.substring(0, string.length() - 2);
    }

    private boolean isPCRelative(int displacement) {
        return displacement >= -2048 && displacement <= 2047;
    }

    private boolean isBaseRelative(int displacement) {
        if (Program.hasBaseDirective()) return displacement >= 0 && displacement <= 4095;
        return false;
    }

    /**
     * Inner class for generating and handling single record
     */
    // TODO
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

        private boolean isLengthExceeding() {
            return content.length() - (lines * 68) >= 0;
        }
    }

    public Terminal terminal = new Terminal();

    /**
     * Utility class to print files content in terminal
     */
    public class Terminal {

        private Terminal() {
        }

        public void show() {
            headerMessage("Object Code");
            System.out.println(headerRecord.content.toString());
            System.out.println(textRecord.content.toString());
            System.out.println(endRecord.content.toString());
        }

        private void headerMessage(String message) {
            System.out.println(ConsoleColors.PURPLE + message + ConsoleColors.RESET);
        }
    }
}
