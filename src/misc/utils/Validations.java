package misc.utils;

import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;

public class Validations {

    public static boolean isComment(String line) {
        return line.startsWith(".");
    }

    public static boolean isBlank(String string) {
        return string != null && !string.isEmpty() && !string.trim().isEmpty();
    }

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOperation(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return DirectiveTable.contains(directive);
    }

    public static boolean isOperation(String operation) {
        return OperationTable.containsMnemonic(operation);
    }
}
