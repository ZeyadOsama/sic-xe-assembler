package main;

import assembler.core.ObjectCodeGenerator;
import assembler.core.OutputGenerator;
import io.FileLoader;
import io.FileWriter;
import parser.Parser;

public class Main {

    public static void main(String[] args) {
        FileLoader fileLoader = new FileLoader();
        fileLoader.openChooserDialogue();

        Parser.getInstance().parse(fileLoader.loadFile(), Parser.Mode.FREE);

        OutputGenerator outputGenerator = new OutputGenerator(fileLoader.getFileParentPath(), fileLoader.getFileName());
        outputGenerator.generateSymbolFile();
        outputGenerator.terminal.showAddressFile();
        outputGenerator.terminal.showSymbolFile();

        ObjectCodeGenerator objectCodeGenerator = new ObjectCodeGenerator();
        objectCodeGenerator.generate();
        objectCodeGenerator.terminal.show();

        FileWriter fileWriter = new FileWriter(outputGenerator);
        fileWriter.writeAddressFile();
        fileWriter.writeSymbolFile();
        fileWriter.writeObjectCodeFile();
    }
}
