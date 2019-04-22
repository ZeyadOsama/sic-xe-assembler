package assembler.core;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;

public class PassOne {

    public static void start() {
        for (Instruction instruction : Assembler.getInstructions()) {
            if (instruction.hasMnemonic() && instruction.getMnemonic().equals(DirectiveTable.START))
                LocationCounter.set(Integer.parseInt(instruction.getFirstOperand()));
            else LocationCounter.reset();
        }
    }
}
