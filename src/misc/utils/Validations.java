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

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOperation(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return (directive != null) && DirectiveTable.contains(directive);
    }

    public static boolean isOperation(String operation) {
        return (operation != null) && OperationTable.containsMnemonic(operation);
    }
}
