package parser;

import assembler.core.LocationCounter;
import assembler.structure.Instruction;
import misc.exceptions.ParsingException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static misc.utils.Validations.*;

/**
 * Responsible for reading an input text file for SIC and SIC/XE code.
 * <p>
 * Possibly containing comments or unexpected characters. parses it an creates
 * an arrayList of Instruction in the order they appear in the input file.
 */
public class Parser {

    private static Parser instance = new Parser();

    private ArrayList<Instruction> parsedInstructions = new ArrayList<>();
    private ArrayList<String> instructions = new ArrayList<>();
    private Instruction lastParsedInstruction;
    private LocationCounter locationCounter = LocationCounter.getInstance();

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

    private Parser() {
    }

    public static Parser getInstance() {
        return instance;
    }

    /**
     * Parses the file specified in the path.
     * Reads it line by line and creates a list of instructions in the same order
     * they appear in the file
     *
     * @param bufferedReader file being read
     * @return ArrayList containing parsed instructions
     * @throws ParsingException in case the input file contains unexpected text
     * @see Instruction class
     */
    public ArrayList<Instruction> parse(BufferedReader bufferedReader) throws ParsingException {
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                instructions.add(line);
                parsedInstructions.add(parseInstruction(line));
            }
        } catch (IOException e) {
            e.getCause();
        }
        return parsedInstructions;
    }

    /**
     * Parses a single instruction given in the form of a String into Instructon form
     *
     * @param instruction is a String which is read from file
     * @return parsed instructions
     * @throws ParsingException in case the input file contains unexpected text
     * @see Instruction class
     */
    public Instruction parseInstruction(String instruction) throws ParsingException {
        String label = null, mnemonic = null, operands = null, comment = null;
        String[] operandsList = new String[2];

        if (isComment(instruction)) {
            locationCounter.update();
            return new Instruction();
        }

        if (instruction.length() > Range.LABEL[Range.END]) {
            label = instruction.substring(Range.LABEL[Range.START], Range.LABEL[Range.END]);
            if (isBlank(label)) label = null;
        }

        if (instruction.length() > Range.MNEMONIC[Range.END]) {
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
                    .replaceAll("\\s", "");
            if (!isMnemonic(mnemonic)) mnemonic = null;
        } else if (instruction.length() > Range.MNEMONIC[Range.START]) {
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START])
                    .replaceAll("\\s", "");
            if (!isMnemonic(mnemonic)) mnemonic = null;
        }

        if (instruction.length() > Range.COMMENT[Range.START]) {
            operands = instruction.substring(Range.OPERANDS[Range.START], Range.OPERANDS[Range.END])
                    .replaceAll("\\s", "");
            comment = instruction.substring(Range.COMMENT[Range.START]);
            if (isBlank(comment)) comment = null;

        } else if (instruction.length() > Range.OPERANDS[Range.START])
            operands = instruction.substring(Range.OPERANDS[Range.START])
                    .replaceAll("\\s", "");

        if (operands != null) {
            int i = 0;
            StringTokenizer tokenizer = new StringTokenizer(operands, ",");
            while (tokenizer.hasMoreTokens())
                operandsList[i++] = tokenizer.nextToken();
        }
        lastParsedInstruction
                = new Instruction(label, mnemonic, operandsList[FIRST_OPERAND], operandsList[SECOND_OPERAND], comment);
        parsedInstructions.add(lastParsedInstruction);
        locationCounter.update(lastParsedInstruction);
        return lastParsedInstruction;
    }

    /**
     * @return ArrayList containing all parsed instructions of the given program
     * @see Instruction
     */
    public ArrayList<Instruction> getParsedInstructions() {
        return parsedInstructions;
    }

    /**
     * @return ArrayList containing all instructions of the given program in
     * it's original form {String}
     */
    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public Instruction getLastParsedInstruction() {
        return lastParsedInstruction;
    }

    /**
     * Utility method.
     * Show all instructions of read program in form of their details.
     */
    public void showParsedInstructions() {
        for (Instruction i : parsedInstructions) {
            System.out.println(i.getLabel());
            System.out.println(i.getMnemonic());
            System.out.println(i.getFirstOperand());
            System.out.println(i.getSecondOperand());
            System.out.println(i.getComment() + "\n");
        }
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
