package misc.utils;

import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;

public class Validations {

    public static boolean isMnemonic(String mnemonic) {
        return isDirective(mnemonic) || isOpcode(mnemonic);
    }

    public static boolean isDirective(String directive) {
        return DirectiveTable.contains(directive);
    }

    public static boolean isOpcode(String opcode) {
        return OperationTable.containsKey(opcode);
    }
}
