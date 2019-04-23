package assembler.core;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import parser.Parser;

public class PassOne {

    public static void start() {
        Parser parser = Parser.getInstance();
        for (Instruction instruction : parser.getParsedInstructionsList()) {
            if (instruction.hasMnemonic() && instruction.getMnemonic().equals(DirectiveTable.START))
                LocationCounter.set(instruction.getFirstOperand());
            else LocationCounter.reset();
        }
    }
}
