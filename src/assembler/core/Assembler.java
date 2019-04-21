package assembler.core;

import java.util.ArrayList;

public final class Assembler {

    private static ArrayList<String> programLines = new ArrayList<>();
    private Assembler instance = new Assembler();

    private Assembler() {
    }

    public static ArrayList<String> getProgramLines() {
        return programLines;
    }

    public static void addProgramLine(String line) {
        programLines.add(line);
    }

    public static String getProgramLine(int index) {
        return programLines.get(index);
    }

    public Assembler getInstance() {
        return instance;
    }
}
