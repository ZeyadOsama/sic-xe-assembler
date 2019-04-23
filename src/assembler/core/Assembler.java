package assembler.core;

/**
 * Responsible for having all instructions of current read program.
 */
public final class Assembler {

    private static Assembler instance = new Assembler();

    public static Assembler getInstance() {
        return instance;
    }

    private static int instructionCount = 0;

    private Assembler() {
    }

    public static int getInstructionCount() {
        return instructionCount;
    }

    public static void resetInstructionCount() {
        Assembler.instructionCount = 0;
    }
}
