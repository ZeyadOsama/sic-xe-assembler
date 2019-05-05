package assembler.core;

import misc.constants.Constants;
import misc.utils.ConsoleColors;

public final class ErrorHandler {

    private static ErrorHandler instance = new ErrorHandler();


    public final static int MISPLACED_LABEL = 0;
    public final static int MISPLACED_OPERATION = 1;
    public final static int MISPLACED_OPERAND = 2;
    public final static int DUPLICATE_LABEL = 3;
    public final static int CAN_NOT_HAVE_LABEL = 4;
    public final static int CAN_NOT_HAVE_OPERANDS = 5;
    public final static int WRONG_OPERATION = 6;
    public final static int UNRECOGNIZED_OPERATION = 7;
    public final static int UNDEFINED_SYMBOL = 8;
    public final static int NOT_HEX = 9;
    public final static int NOT_FORMAT_FOUR = 10;
    public final static int ILLEAGAL_REG_ADDRESS = 11;
    public final static int MISSING_END = 12;
    public final static int LABEL_STARTING_WITH_DIGIT = 13;
    public final static int WRONG_COMMENT_FORMAT = 14;
    public final static int LABELS_CAN_NOT_HAVE_SPACES = 15;
    public final static int NO_FIRST_OPERAND = 16;
    public final static int NO_SECOND_OPERAND = 17;
    public final static int SHOULD_HAVE_FIRST_OPERAND = 18;
    public final static int SHOULD_HAVE_SECOND_OPERAND = 19;
    public final static int WRONG_OPERAND_TYPE = 20;
    public final static int MISSING_MNEMONIC = 21;


    private final static String[] errorList = new String[22];
    private boolean hasError = false;
    private int currentError;
    private boolean isNonExecutable = false;

    private ErrorHandler() {
    }

    public static ErrorHandler getInstance() {
        return instance;
    }

    public String getErrorString(int error) {
        return errorList[error];
    }

    public int getCurrentError() {
        return currentError;
    }

    public void setCurrentError(int currentError) {
        this.currentError = currentError;
    }

    private static void load() {
        errorList[0] = "***ERROR: misplaced label***";
        errorList[1] = "***ERROR: missing or misplaced operation mnemonic***";
        errorList[2] = "***ERROR: missing or misplaced operand field***";
        errorList[3] = "***ERROR: duplicate label definition***";
        errorList[4] = "***ERROR: this statement can't have a label***";
        errorList[5] = "***ERROR: this statement can't have an operands***";
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
        errorList[16] = "***ERROR: can not have operand***";
        errorList[17] = "***ERROR: can not have second operand***";
        errorList[18] = "***ERROR: should have an operand***";
        errorList[19] = "***ERROR: should have second operand***";
        errorList[20] = "***ERROR: wrong operand type***";
        errorList[21] = "***ERROR: missing mnemonic***";
    }

    public boolean hasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
        if (hasError)
            isNonExecutable = true;
    }

    public String generate() {
        return ConsoleColors.RED + Constants.TAB + errorList[currentError] + ConsoleColors.RESET;
    }

    public boolean isNonExecutable() {
        return isNonExecutable;
    }

    static {
        load();
    }
}
