package assembler.core;

public final class OutputGenerator {

    private OutputGenerator() {
    }

    private static StringBuilder outputString = new StringBuilder();

    public static void resetOuput() {
        outputString = new StringBuilder();
    }

    public static void append(String text) {
        outputString.append(text);
    }

    public static void appendLine(String line) {
        String newLine = "\n" + line;
        outputString.append(newLine);
    }
}
