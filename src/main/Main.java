package main;

import assembler.core.OutputGenerator;
import io.FileLoader;
import parser.Parser;

public class Main {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();

        Parser parser = Parser.getInstance();
        parser.parse(fileLoader.loadFile());

        OutputGenerator outputGenerator = new OutputGenerator(fileLoader.getFileParentPath(), fileLoader.getFileName());
        outputGenerator.generate();
        outputGenerator.showInTerminal();
        outputGenerator.makeAddressFile();
    }
}
