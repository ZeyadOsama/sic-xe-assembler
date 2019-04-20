package assembler.core;

public class OutputGenerator {

    private static OutputGenerator instance = new OutputGenerator();
    private static StringBuilder outputString = new StringBuilder();

    private OutputGenerator() {
    }

    public static void resetOuput(){
        outputString = new StringBuilder();
    }

    public static void append(String text){
        outputString.append(text);
    }

    public OutputGenerator getInstance() {
        return instance;
    }
}
