package assembler.core;

import parser.Parser;

public class Program {

    private static int startAddress;
    private static int endAddress;
    private static int instructionCount;
    private static boolean hasBaseDirective = Parser.getInstance().hasBaseDirective();

    public static int getStartAddress() {
        return startAddress;
    }

    public static int getEndAddress() {
        return endAddress;
    }

    public static int getInstructionCount() {
        return instructionCount;
    }

    public static boolean hasBaseDirective() {
        return hasBaseDirective;
    }
}
