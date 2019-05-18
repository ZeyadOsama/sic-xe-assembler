package parser;

import assembler.constants.Format;
import assembler.constants.OperandType;
import assembler.core.ErrorHandler;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import assembler.tables.RegisterTable;
import misc.constants.Constants;

import java.util.StringTokenizer;

import static misc.utils.Validations.*;

class ParsingValidations {

    private static ErrorHandler errorHandler = ErrorHandler.getInstance();
    private static Instruction instruction;

    static boolean validateInstruction(Instruction instruction) {
        ParsingValidations.instruction = instruction;
        if (validateLabel())
            if (validateMnemonic())
                return validateOperands();
        return false;
    }

    private static boolean validateLabel() {
        if (instruction.getLabel() == null) {
            errorHandler.setHasError(false);
            return true;
        }

        if (startsWithNumber(instruction.getLabel())) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.LABEL_STARTING_WITH_DIGIT);
            return false;
        }

        byte cnt = 0;
        StringTokenizer tokenizer = new StringTokenizer(instruction.getLabel(), Constants.SPACE);
        while (tokenizer.hasMoreTokens()) {
            tokenizer.nextToken();
            cnt++;
        }
        if (cnt > 1) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.LABELS_CAN_NOT_HAVE_SPACES);
            return false;
        }
        instruction.setLabel(instruction.getLabel().replaceAll("\\s", ""));
        return true;

    }

    private static boolean validateMnemonic() {
        if (instruction.getMnemonic() == null) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.MISSING_MNEMONIC);
            return false;
        }

        // removes format four symbol first to check existence of mnemonic
        String mnemonic = instruction.getMnemonic();
        boolean hasFormatFourSymbol = false;
        if (mnemonic.startsWith("+")) {
            mnemonic = mnemonic.replace("+", "");
            hasFormatFourSymbol = true;
        }

        if (!isMnemonic(mnemonic)) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.UNRECOGNIZED_OPERATION);
            return false;
        }

        // if mnemonic exists check it's format
        if (hasFormatFourSymbol) {
            mnemonic = "+" + mnemonic;
            if (!OperationTable.containsMnemonic(mnemonic)
                    || OperationTable.getOperation(mnemonic).getFormat() != Format.FOUR) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.NOT_FORMAT_FOUR);
                return false;
            }
        }

        errorHandler.setHasError(false);
        return true;
    }

    private static boolean validateOperands() {
        if (instruction.getMnemonic() == null && instruction.hasOperands()) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.CAN_NOT_HAVE_OPERANDS);
            return false;
        }
        return isOperation(instruction.getMnemonic()) ? validateOperationOperands() : validateDirectiveOperands();
    }

    private static boolean validateDirectiveOperands() {
        if (DirectiveTable.getDirective(instruction.getMnemonic()).hasOperand()) {
            if (!instruction.hasFirstOperand()) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.SHOULD_HAVE_FIRST_OPERAND);
                return false;
            }
        } else if (instruction.hasFirstOperand()) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.NO_FIRST_OPERAND);
            return false;
        }
        errorHandler.setHasError(false);
        return true;
    }

    private static boolean validateOperationOperands() {
        if (OperationTable.getOperation(instruction.getMnemonic()).hasFirstOperand()) {
            if (!instruction.hasFirstOperand()) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.SHOULD_HAVE_FIRST_OPERAND);
                return false;
            }
            if (OperationTable.getOperation(instruction.getMnemonic()).getFirstOperandType() == OperandType.REGISTER)
                if (!RegisterTable.contains(instruction.getFirstOperand())) {
                    errorHandler.setHasError(true);
                    errorHandler.setCurrentError(ErrorHandler.WRONG_OPERAND_TYPE);
                    return false;
                }
        } else if (instruction.hasFirstOperand()) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.NO_FIRST_OPERAND);
            return false;
        }
        if (OperationTable.getOperation(instruction.getMnemonic()).hasSecondOperand()) {
            if (!instruction.hasSecondOperand()) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.SHOULD_HAVE_SECOND_OPERAND);
                return false;
            }
            if (OperationTable.getOperation(instruction.getMnemonic()).getSecondOperandType() == OperandType.REGISTER)
                if (!RegisterTable.contains(instruction.getSecondOperand())) {
                    errorHandler.setHasError(true);
                    errorHandler.setCurrentError(ErrorHandler.WRONG_OPERAND_TYPE);
                    return false;
                }
        } else if (instruction.hasSecondOperand()
                && OperationTable.getOperation(instruction.getMnemonic()).getSecondOperandType() != OperandType.DONT_CARE) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.NO_SECOND_OPERAND);
            return false;
        }
        errorHandler.setHasError(false);
        return true;
    }


    private static boolean validateComment(String comment) {
        errorHandler.setHasError(false);
        return true;
    }
}


