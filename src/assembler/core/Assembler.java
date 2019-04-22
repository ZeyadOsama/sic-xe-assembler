package assembler.core;

import assembler.structure.Instruction;

import java.util.ArrayList;

/**
 * Responsible for having all instructions of current read program.
 */
public final class Assembler {

    private Assembler instance = new Assembler();

    public Assembler getInstance() {
        return instance;
    }

    private static ArrayList<Instruction> programInstructions = new ArrayList<>();
    private static int instructionCount = 0;

    private Assembler() {
    }

    public static void addInstruction(Instruction instruction) {
        instructionCount++;
        programInstructions.add(instruction);
    }

    public static Instruction getInstruction(int index) {
        return programInstructions.get(index);
    }

    public static ArrayList<Instruction> getInstructions() {
        return programInstructions;
    }

    public static int getInstructionCount() {
        return instructionCount;
    }

    public static void resetInstructionCount() {
        Assembler.instructionCount = 0;
    }

    /**
     * Utility method.
     * Show all instructions of read program in form of their details.
     */
    public static void showInstructions() {
        for (Instruction i : programInstructions) {
            System.out.println(i.getLabel());
            System.out.println(i.getMnemonic());
            System.out.println(i.getFirstOperand());
            System.out.println(i.getSecondOperand());
            System.out.println(i.getComment() + "\n");
        }
    }
}
