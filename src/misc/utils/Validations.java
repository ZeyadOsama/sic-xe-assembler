package misc.utils;

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

    public static boolean isMnemonic(@NotNull String mnemonic) {
        return isDirective(mnemonic) || isOperation(mnemonic);
    }

    public static boolean isDirective(@NotNull String directive) {
        return DirectiveTable.contains(directive);
    }

    public static boolean isOperation(@NotNull String operation) {
        return OperationTable.containsMnemonic(operation);
    }
}
