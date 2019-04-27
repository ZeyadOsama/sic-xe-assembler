package parser;

import assembler.constants.OperandType;
import assembler.core.ErrorHandler;
import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import assembler.tables.RegisterTable;
import misc.constants.Constants;

import java.util.StringTokenizer;

import static misc.utils.Validations.isMnemonic;
import static misc.utils.Validations.isOperation;

class ParsingValidations {

    private static Instruction instruction;

    private static ErrorHandler errorHandler = ErrorHandler.getInstance();

    static boolean validateInstruction(Instruction instruction) {
        ParsingValidations.instruction = instruction;
        return validateLabel() && validateMnemonic() && validateOperands();
    }

    private static boolean validateLabel() {
        if (instruction.getLabel() != null) {
            byte cnt = 0;
            StringTokenizer tokenizer = new StringTokenizer(instruction.getLabel(), Constants.SPACE);
            while (tokenizer.hasMoreTokens()){
                tokenizer.nextToken();
                cnt++;
            }
            if (cnt > 1) {
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.LABELS_CAN_NOT_HAVE_SPACES);
                return false;
            }
        }
        errorHandler.setHasError(false);
        return true;
    }

    private static boolean validateMnemonic() {
        if (instruction.getMnemonic() == null) {
            errorHandler.setHasError(false);
            return true;
        }
        if (!isMnemonic(instruction.getMnemonic())) {
            errorHandler.setHasError(true);
            errorHandler.setCurrentError(ErrorHandler.UNRECOGNIZED_OPERATION);
            return false;
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
        } else if (instruction.hasSecondOperand()) {
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


