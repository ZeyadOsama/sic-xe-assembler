package misc.utils;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import org.jetbrains.annotations.NotNull;

/**
 * Utility class for parsing validations.
 * Needed for error detection.
 */
public class Validations {

    public static boolean isComment(@NotNull String line) {
        return line.startsWith(".");
    }

    public static boolean isBlank(@NotNull String string) {
        return !string.isEmpty() && string.trim().isEmpty();
    }

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOperation(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return (directive != null) && DirectiveTable.contains(directive);
    }

    public static boolean isReservationDirective(String directive) {
        return (directive != null)
                && (directive.equals(DirectiveTable.RESB) || directive.equals(DirectiveTable.RESW));
    }

    public static boolean isDeclartiveDirective(String directive) {
        return (directive != null)
                && (directive.equals(DirectiveTable.BYTE) || directive.equals(DirectiveTable.WORD));
    }

    public static boolean isOperation(String operation) {
        return (operation != null) && OperationTable.containsMnemonic(operation);
    }

    public static class Operand {

        public static boolean isImmediate(Instruction instruction) {
            return (instruction.getFirstOperand() != null && instruction.getFirstOperand().startsWith("#"))
                    || (instruction.getSecondOperand() != null && instruction.getSecondOperand().startsWith("#"));
        }

        public static boolean isIndirect(Instruction instruction) {
            return (instruction.getFirstOperand() != null && instruction.getFirstOperand().startsWith("@"))
                    || (instruction.getSecondOperand() != null && instruction.getSecondOperand().startsWith("@"));
        }

        public static boolean isDirect(Instruction instruction) {
            return !isImmediate(instruction) && !isIndirect(instruction);
        }

        public static boolean isImmediate(String operand) {
            return operand != null && operand.startsWith("#");
        }

        public static boolean isIndirect(String operand) {
            return operand != null && operand.startsWith("@");
        }

        public static boolean isDirect(String operand) {
            return !isImmediate(operand) && !isIndirect(operand);
        }

        public static boolean isLiteral(String operand) {
            return operand != null && (operand.startsWith("X'") || operand.startsWith("C'")) && operand.endsWith("'");
        }
    }
}
