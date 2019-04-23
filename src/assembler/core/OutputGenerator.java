package assembler.core;

import parser.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class OutputGenerator {

    private static OutputGenerator instance = new OutputGenerator();
    private ArrayList<String> output = new ArrayList<>();

    private OutputGenerator() {
    }

    public static OutputGenerator getInstance() {
        return instance;
    }

    public void generate() {
        LocationCounter locationCounter = LocationCounter.getInstance();
        for (int i = 0; i < locationCounter.getProgramCounter(); i++) {
            output.add(locationCounter.getHexAddresses().get(i)
                    + "\t\t"
                    + Parser.getInstance().getInstructions().get(i));
        }
    }

    public void showInTerminal() {
        for (String line : output)
            System.out.println(line);
    }

    public void saveToFile(String filePath, String fileName) {

        String file = filePath + "/" + fileName.replaceAll(".txt", "") + "-addresses.txt";
        System.out.println(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (String string : output) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
    }
}
