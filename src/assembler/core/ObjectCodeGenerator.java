package assembler.core;

import assembler.constants.Format;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import assembler.tables.RegisterTable;
import assembler.tables.SymbolTable;
import misc.utils.ConsoleColors;
import misc.utils.ExpressionEvaluator;
import misc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Converter.Binary;
import static misc.utils.Converter.Decimal;
import static misc.utils.Utils.extendLength;
import static misc.utils.Utils.parseDataOperand;
import static misc.utils.Validations.Operand.*;
import static misc.utils.Validations.isExpression;
import static misc.utils.Validations.isOperation;

public final class ObjectCodeGenerator {
    private int PC;
    private int PC_LAST;
    private int displacement;
    private Record headerRecord;
    private Record endRecord;
    private ArrayList<Record> textRecords;
    private ArrayList<Instruction> parsedInstructions;
    private ArrayList<Integer> addresses;
    private SymbolTable symbolTable;

    public ObjectCodeGenerator() {
        PC = PC_LAST = displacement = 0;

        textRecords = new ArrayList<>();
        headerRecord = new Record(Record.HEADER);
        endRecord = new Record(Record.END);

        symbolTable = SymbolTable.getInstance();
        parsedInstructions = Parser.getInstance().getParsedInstructions();
        addresses = LocationCounter.getInstance().getAddresses();
    }

    /**
     * Terminal instance
     */
    public Terminal terminal = new Terminal();

    public String getHeaderRecord() {
        return headerRecord.getContent().append("\n").toString();
    }

    public String getTextRecords() {
        StringBuilder builder = new StringBuilder();
        for (Record record : textRecords)
            builder.append(record.getContent().toString()).append("\n");
        return builder.toString();
    }

    public String getEndRecord() {
        return endRecord.getContent().append("\n").toString();
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

    private void generateTextRecord() {
        ArrayList<ObjectCode> objectCodes = new ArrayList<>();
        int i = 0;
        for (Instruction instruction : parsedInstructions) {
            if (i < addresses.size() - 1) {
                PC_LAST = addresses.get(i);
                PC = addresses.get(++i);
            }
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
            } else if (instruction.getMnemonic().equals(DirectiveTable.BYTE)) {
                String operand = instruction.getFirstOperand();
                if (isLiteral(operand)) {
                    String[] ocStream = Utils.splitAfter(parseDataOperand(operand), 6);
                    for (String oc : ocStream)
                        objectCodes.add(
                                new ObjectCode(extendLength(oc, Utils.ceilToEven(oc.length())),
                                        instruction.getAddress()));
                } else
                    objectCode = extendLength(Decimal.toHexadecimal(operand), Utils.ceilToEven(operand.length()));
            } else if (instruction.getMnemonic().equals(DirectiveTable.WORD)) {
                String operand = instruction.getFirstOperand();
                if (isLiteral(operand))
                    operand = parseDataOperand(operand);
                objectCode = extendLength(Decimal.toHexadecimal(operand), 6);
            }
            if (objectCode != null)
                objectCodes.add(new ObjectCode(objectCode, instruction.getAddress()));
        }

        int recordLength = 0, addressIndex = 0;
        Record textRecord = new Record(Record.TEXT);
        for (int j = 0; j < objectCodes.size(); j++) {
            String objectCode = objectCodes.get(j).getObjectCode();
            if ((textRecord.getContent().length() + (objectCode.length())) > Record.MAX_STORAGE) {
                textRecord.insertFirst(extendLength(Decimal.toHexadecimal(recordLength), 2));
                textRecord.insertFirst(extendLength(
                        Decimal.toHexadecimal(objectCodes.get(addressIndex).getInstructionAddress()), 6));
                textRecords.add(textRecord);
                textRecord = new Record(Record.TEXT);
                addressIndex = j;
                recordLength = 0;
            }
            recordLength += objectCode.length() / 2;
            textRecord.addContent(objectCode);

            if (j == objectCodes.size() - 1) {
                textRecord.insertFirst(extendLength(Decimal.toHexadecimal(recordLength), 2));
                textRecord.insertFirst(extendLength(
                        Decimal.toHexadecimal(objectCodes.get(j).getInstructionAddress()), 6));
                textRecords.add(textRecord);
            }
        }
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
        return extendLength(Binary.toHexadecimal(opcode
                + getNIX(instruction) + getBPE(instruction)
                + extendLength(Decimal.toBinary(displacement), 20)), 8);
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
     * @param instruction parsed
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
            String operand = Utils.removeSpecialSymbol(instruction.getFirstOperand());
            e = '0';

            if (symbolTable.containsSymbol(operand)) {
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
            } else {
                b = '0';
                p = '0';
                if (isAddressSymbol(operand)) {
                    if (isExpression(operand))
                        displacement = ExpressionEvaluator.evaluate(PC_LAST + operand.substring(1));
                    else
                        displacement = PC_LAST;
                } else
                    displacement = Integer.parseInt(operand);
                return String.valueOf(b) + p + e;
            }
        }
        displacement = 0;
        return null;
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
     * Holds object code for a given instruction
     * accompanied with its address
     */
    private class ObjectCode {

        private String objectCode;
        private int instructionAddress;

        private ObjectCode(String objectCode, int instructionAddress) {
            this.objectCode = objectCode;
            this.instructionAddress = instructionAddress;
        }

        private String getObjectCode() {
            return objectCode;
        }

        private int getInstructionAddress() {
            return instructionAddress;
        }
    }

    private void generateEndRecord() {
        endRecord.addContent(Program.getFirstExecutableInstructionAddress());
    }

    /**
     * Inner class for generating and handling single record
     */
    private class Record {

        private static final String SEPARATOR = "^";
        private static final String HEADER = "H";
        private static final String TEXT = "T";
        private static final String END = "E";

        private static final int MAX_STORAGE = 59;

        private StringBuilder content;

        private Record(String recordTitle) {
            content = new StringBuilder();
            content.append(recordTitle);
        }

        public StringBuilder getContent() {
            return content;
        }

        private void addContent(String record) {
            if (record == null)
                return;
            content.append(SEPARATOR).append(record);
        }

        private void insertFirst(String record) {
            content.insert(2, record + SEPARATOR);
        }
    }

    /**
     * Utility class to print files content in terminal
     */
    public class Terminal {

        private Terminal() {
        }

        public void show() {
            headerMessage("Object Code");
            System.out.println(headerRecord.content.toString());
            for (Record textRecord : textRecords)
                System.out.println(textRecord.content.toString());
            System.out.println(endRecord.content.toString());
        }

        private void headerMessage(String message) {
            System.out.println(ConsoleColors.PURPLE + message + ConsoleColors.RESET);
        }
    }
}
