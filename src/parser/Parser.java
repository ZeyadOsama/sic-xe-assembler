package parser;

import assembler.structure.Instruction;

import java.util.StringTokenizer;

import static misc.utils.Validations.*;

public class Parser {

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

    /**
     * Parse a given String to the format of Instruction class.
     *
     * @param instruction is a String which is read from file
     * @return Instruction format
     * @see Instruction class
     */
    public Instruction parse(String instruction) {
        String label = null, mnemonic = null, operands = null, comment = null;
        String[] operandsList = new String[2];

        if (isComment(instruction)) return new Instruction();

        if (instruction.length() > Range.LABEL[Range.END]) {
            label = instruction.substring(Range.LABEL[Range.START], Range.LABEL[Range.END]);
            if (isBlank(label)) label = null;
        }

        if (instruction.length() > Range.MNEMONIC[Range.END]) {
            mnemonic = instruction.substring(Range.MNEMONIC[Range.START], Range.MNEMONIC[Range.END])
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

        return new Instruction(label, mnemonic, operandsList[FIRST_OPERAND], operandsList[SECOND_OPERAND], comment);
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
