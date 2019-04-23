package assembler.core;

import parser.Parser;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class OutputGenerator {

    private String filePath;
    private String fileName;
    private ArrayList<String> addressFile;
    private ArrayList<String> symbolFile;

    public OutputGenerator(String filePath, String fileName) {
        this.filePath = filePath;
        this.fileName = fileName;
        addressFile = new ArrayList<>();
    }

    public void generate() {
        LocationCounter locationCounter = LocationCounter.getInstance();
        for (int i = 0; i < locationCounter.getProgramCounter(); i++) {
            addressFile.add(locationCounter.getHexAddresses().get(i)
                    + "\t\t"
                    + Parser.getInstance().getInstructions().get(i));
        }
    }

    public void showInTerminal() {
        for (String line : addressFile)
            System.out.println(line);
    }

    public void makeAddressFile() {
        String file = filePath + "/" + fileName.replaceAll(".txt", "") + "-addresses.txt";
        System.out.println(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (String string : addressFile) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }
    }

    public void makeSymbolFile() {
        String file = filePath + "/" + fileName.replaceAll(".txt", "") + "-symbol-table.txt";
        System.out.println(file);
        try {
            FileWriter writer = new FileWriter(file);
            for (String string : symbolFile) {
                writer.write(string + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.getCause();
        }

    }
}
