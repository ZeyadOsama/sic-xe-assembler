package parser;

import assembler.core.ErrorHandler;
import assembler.core.LocationCounter;
import assembler.core.OutputGenerator;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.SymbolTable;
import misc.constants.Constants;
import misc.exceptions.ParsingException;
import misc.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;
import java.util.StringTokenizer;

import static misc.utils.Validations.isBlank;
import static misc.utils.Validations.isComment;
import static parser.ParsingValidations.validateInstruction;

/**
 * Responsible for reading an input text file for SIC and SIC/XE code.
 * <p>
 * Possibly containing comments or unexpected characters. parses it an creates
 * an arrayList of Instruction in the order they appear in the input file.
 */
public class Parser {

    private static Parser instance = new Parser();

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

    private Parser() {
    }

    public static Parser getInstance() {
        return instance;
    }

    private ArrayList<Instruction> parsedInstructionsList = new ArrayList<>();
    private String currentInstruction;
    private boolean hasBaseDirective = false;

    private LocationCounter locationCounter = LocationCounter.getInstance();
    private SymbolTable symbolTable = SymbolTable.getInstance();
    private ErrorHandler errorHandler = ErrorHandler.getInstance();

    /**
     * Parses the file specified in the path.
     * Reads it line by line and creates a list of instructions in the same order
     * they appear in the file
     *
     * @param instructions as un-parsed ArrayList {String} containing program line by line
     * @param mode         either free format or constrained format
     * @return ArrayList containing parsed instructions
     * @see Instruction class
     * @see Mode enum
     */
    public ArrayList<Instruction> parse(@NotNull ArrayList<String> instructions, @NotNull Mode mode) {
        Instruction parsedInstruction;
        for (String instruction : instructions) {
            parsedInstruction = parseInstruction(instruction, mode);
            if (parsedInstruction != null)
                parsedInstructionsList.add(parsedInstruction);
        }
        return parsedInstructionsList;
    }


    /**
     * Parses a single instruction given in the form of a String into Instruction form
     *
     * @param instruction is a String which is read from file
     * @param mode        either free format or constrained format
     * @return parsed instruction
     * @throws ParsingException in case the input file contains unexpected text
     * @see Instruction class
     * @see Mode enum
     */
    @Nullable
    public Instruction parseInstruction(@NotNull String instruction, @NotNull Mode mode) {
        errorHandler.setHasError(false);
        if (isComment(instruction)) {
            locationCounter.update();
            OutputGenerator.update();
            return new Instruction();
        }
        Instruction parsedInstruction = null;
        if (mode == Mode.FREE)
            parsedInstruction = parseInstructionFree(instruction);
        else if (mode == Mode.CONSTRAINED)
            parsedInstruction = parseInstructionConstrained(instruction);

        if (parsedInstruction == null)
            return null;

        currentInstruction = parsedInstruction.toString();
        parsedInstruction.errorFree(validateInstruction(parsedInstruction));
        locationCounter.update(parsedInstruction);
        symbolTable.update(parsedInstruction);
        OutputGenerator.update();
        return parsedInstruction;
    }

    /**
     * Parses a single constrained format instruction given in the form of a String into Instruction form
     * Gets called internally according to parsing mode
     *
     * @param instruction is a String which is read from file
     * @return parsed instructions
     * @throws ParsingException in case the input file contains unexpected text
     * @see Instruction class
     * @see Mode enum
     */
    @NotNull
    private Instruction parseInstructionConstrained(@NotNull String instruction) throws ParsingException {
        String label = determineLabel(instruction);
        String mnemonic = determineMnemonic(instruction);
        String[] operandsList = determineOperands(instruction);
        String comment = determineComment(instruction);

        return new Instruction(label,
                Objects.requireNonNull(mnemonic),
                Objects.requireNonNull(operandsList)[FIRST_OPERAND],
                Objects.requireNonNull(operandsList)[SECOND_OPERAND],
                comment);
    }

    /**
     * Parses a single free format instruction given in the form of a String into Instruction form
     * Gets called internally according to parsing mode
     *
     * @param instruction is a String which is read from file
     * @return parsed instructions
     * @throws ParsingException in case the input file contains unexpected text
     * @see Instruction class
     * @see Mode enum
     */
    @Nullable
    private Instruction parseInstructionFree(@NotNull String instruction) throws ParsingException {
        String label = null, mnemonic = null, operands = null, comment = null;
        String[] operandsList = new String[2];

        Stack<String> instructionElements = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(instruction, Constants.SPACE);
        while (tokenizer.hasMoreTokens())
            instructionElements.push(tokenizer.nextToken());

        switch (instructionElements.size()) {
            case 0:
                return null;
            case 1:
                mnemonic = instructionElements.pop();
                break;
            case 2:
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                break;
            case 3:
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                label = instructionElements.pop();
                break;
            default:
                StringBuilder commentBuilder = new StringBuilder();
                while (instructionElements.size() > 3)
                    commentBuilder.append(instructionElements.pop()).append(Constants.SPACE);
                comment = Utils.reverseWords(commentBuilder.toString());
                operands = instructionElements.pop();
                mnemonic = instructionElements.pop();
                label = instructionElements.pop();
                break;
        }
        if (operands != null) {
            int i = 0;
            StringTokenizer operandsTokenizer = new StringTokenizer(operands, Constants.COMMA);
            while (operandsTokenizer.hasMoreTokens())
                operandsList[i++] = operandsTokenizer.nextToken();
        }
        if (mnemonic != null && mnemonic.equals(DirectiveTable.BASE))
            hasBaseDirective = true;

        return new Instruction(label,
                Objects.requireNonNull(mnemonic),
                Objects.requireNonNull(operandsList)[FIRST_OPERAND],
                Objects.requireNonNull(operandsList)[SECOND_OPERAND],
                comment);
    }

    @Nullable
    private String determineLabel(@NotNull String instruction) {
        String label = null;
        if (instruction.length() > Range.LABEL[Range.END]) {
            label = instruction.substring(Range.LABEL[Range.START], Range.LABEL[Range.END]);
            if (isBlank(label))
                return null;
        }
        return label;
    }

    @Nullable
    private String determineMnemonic(@NotNull String instruction) {
        String mnemonic = null;
        if (instruction.length() > Range.MNEMONIC[Range.END])
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
                    .replaceAll("\\s", Constants.EMPTY);

        else if (instruction.length() > Range.MNEMONIC[Range.START])
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START])
                    .replaceAll("\\s", Constants.EMPTY);

        if (mnemonic != null && mnemonic.equals(DirectiveTable.BASE))
            hasBaseDirective = true;

        return mnemonic;
    }

    @Nullable
    private String[] determineOperands(@NotNull String instruction) {
        String operands = null;
        if (instruction.length() > Range.OPERANDS[Range.END])
            operands = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
                    .replaceAll("\\s", Constants.EMPTY);

        else if (instruction.length() > Range.OPERANDS[Range.START])
            operands = instruction.substring(Range.OPERANDS[Range.START])
                    .replaceAll("\\s", Constants.EMPTY);

        String[] operandsList = new String[2];
        if (operands != null) {
            int i = 0;
            StringTokenizer tokenizer = new StringTokenizer(operands, Constants.COMMA);
            while (tokenizer.hasMoreTokens())
                operandsList[i++] = tokenizer.nextToken();
        }
        return operandsList;
    }

    @Nullable
    private String determineComment(@NotNull String instruction) {
        String comment = null;
        if (instruction.length() > Range.COMMENT[Range.START]) {
            comment = instruction.substring(Range.COMMENT[Range.START]);
            if (isBlank(comment))
                return null;
        }
        return comment;
    }

    /**
     * @return ArrayList containing all parsed instructions of the given program
     * @see Instruction
     */
    public ArrayList<Instruction> getParsedInstructions() {
        return parsedInstructionsList;
    }

    public String getCurrentInstruction() {
        return currentInstruction;
    }

    /**
     * Utility method.
     * Show all instructions of read program in form of their details.
     */
    public void showParsedInstructions() {
        for (Instruction i : parsedInstructionsList) {
            System.out.println(i.getLabel());
            System.out.println(i.getMnemonic());
            System.out.println(i.getFirstOperand());
            System.out.println(i.getSecondOperand());
            System.out.println(i.getComment() + "\n");
        }
    }

    public boolean hasBaseDirective() {
        return hasBaseDirective;
    }

    public enum Mode {
        CONSTRAINED, FREE
    }

    /**
     * Utility inner class for constants of instruction format range.
     */
    private static class Range {
        private final static int START = 0;
        private final static int END = 1;
        private final static int[] LABEL = {0, 9};
        private final static int[] MNEMONIC = {9, 16};
        private final static int[] OPERANDS = {17, 36};
        private final static int[] COMMENT = {35, 67};
    }
}
