package assembler.core;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import parser.Parser;

public class PassOne {

    public static void start() {
        Parser parser = Parser.getInstance();
        LocationCounter locationCounter = LocationCounter.getInstance();
        for (Instruction instruction : parser.getParsedInstructions()) {
            if (instruction.hasMnemonic() && instruction.getMnemonic().equals(DirectiveTable.START))
                locationCounter.set(Integer.parseInt(instruction.getFirstOperand()));
            else locationCounter.reset();
        }
    }
}
