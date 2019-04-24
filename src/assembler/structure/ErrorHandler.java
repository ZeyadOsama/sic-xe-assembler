package assembler.structure;

public final class ErrorHandler {

    public final static int MISPLACED_LABEL = 0;
    public final static int MISPLACED_OPERATION = 1;
    public final static int MISPLACED_OPERAND = 2;
    public final static int DUPLICATE_LABEL = 3;

    private final static String[] errorList = new String[17];

    static {
        load();
    }

    private static void load() {
        errorList[0] = "***ERROR: misplaced label***";
        errorList[1] = "***ERROR: missing or misplaced operation mnemonic***";
        errorList[2] = "***ERROR: missing or misplaced operand field***";
        errorList[3] = "***ERROR: duplicate label definition***";
        errorList[4] = "***ERROR: this statement can't have a label***";
        errorList[5] = "***ERROR: this statement can't have an operand***";
        errorList[6] = "***ERROR: wrong operation prefix***";
        errorList[7] = "***ERROR: unrecognized operation code***";
        errorList[8] = "***ERROR: undefined symbol in operand***";
        errorList[9] = "***ERROR: not a hexadecimal string***";
        errorList[10] = "***ERROR: can't be format 4 instruction***";
        errorList[11] = "***ERROR: illegal address for a register***";
        errorList[12] = "***ERROR: missing END statement***";
        errorList[13] = "***ERROR: label cannot start with a digit***";
        errorList[14] = "***ERROR: wrong comment format***";
        errorList[15] = "***ERROR: labels cannot have spaces in between***";
        errorList[16] = "***ERROR: operation mnemonic cannot have spaces in between***";
    }

    public static String getErrorString(int error) {
        return errorList[error];
    }

    public static void validate(){

    }
}
