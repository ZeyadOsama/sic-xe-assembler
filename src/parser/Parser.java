package parser;

import java.util.StringTokenizer;

public class Parser {

    private final static int FIRST_OPERAND = 0;
    private final static int SECOND_OPERAND = 1;

    private String label;
    private String mnemonic;
    private String operands;
    private String[] operandsList = new String[2];
    private String comment;

    public void parse(String line) {

        if (line.startsWith(".")) return;

        if (line.length() > 7) {
            label = line.substring(0, 8).replaceAll("\\s", "");
            ;
            System.out.println("label: " + label);
        }

        if (line.length() > 14) {
            this.mnemonic = line.substring(9, 15).replaceAll("\\s", "");
            ;
            System.out.println("mnemonic: " + mnemonic);
        }

        if (line.length() > 34) {
            this.operands = line.substring(17, 35).replaceAll("\\s", "");
            comment = line.substring(35);
        }

        if (line.length() > 16) {
            this.operands = line.substring(17).replaceAll("\\s", "");
        }

        int i = 0;
        StringTokenizer tokenizer = new StringTokenizer(operands, ",");
        while (tokenizer.hasMoreTokens()) {
            operandsList[i] = tokenizer.nextToken();
            System.out.println("operand " + (i + 1) + ": " + operandsList[i]);
            i++;
        }

        System.out.println();
    }
}
