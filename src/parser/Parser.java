package parser;

import assembler.structure.Instruction;

import java.util.StringTokenizer;

import static misc.utils.Validations.*;

public class Parser {

    private final static int RANGE_LABEL = 9;
    private final static int RANGE_MNEMONIC = 15;
    private final static int RANGE_OPERANDS = 17;
    private final static int RANGE_COMMENT = 35;

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

//    private String label;
//    private String mnemonic;
//    private String operands;
//    private String[] operandsList = new String[2];
//    private String comment;

    public Instruction parse(String line) {
        String label = null, mnemonic = null, operands = null, comment = null;
        String[] operandsList = new String[2];

        if (isComment(line)) return new Instruction();

        if (line.length() > RANGE_LABEL) {
            label = line.substring(0, 9);
            if (isBlank(label)) label = null;
        }

        if (line.length() > RANGE_MNEMONIC) {
            mnemonic = line.substring(9, 16).replaceAll("\\s", "");
            if (!isMnemonic(mnemonic)) mnemonic = null;
        }

        if (line.length() > RANGE_COMMENT) {
            operands = line.substring(15, 36).replaceAll("\\s", "");
            comment = line.substring(35);
            if (isBlank(comment)) comment = null;

        } else if (line.length() > RANGE_OPERANDS)
            operands = line.substring(17).replaceAll("\\s", "");

        if (operands != null) {
            int i = 0;
            StringTokenizer tokenizer = new StringTokenizer(operands, ",");
            while (tokenizer.hasMoreTokens())
                operandsList[i++] = tokenizer.nextToken();
        }

        return new Instruction(label, mnemonic, operandsList[FIRST_OPERAND], operandsList[SECOND_OPERAND], comment);
    }
}
