package main;

import views.MainFrame;

public class Main {

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);

//        FileLoader fileLoader = new FileLoader();
//        fileLoader.openChooserDialogue();

//        Parser.getInstance().parse(fileLoader.loadFile(), Parser.Mode.FREE);
//        Parser.getInstance().parse(mainFrame.loadFile(), Parser.Mode.FREE);

//        OutputGenerator outputGenerator = new OutputGenerator(fileLoader.getFileParentPath(), fileLoader.getFileName());
//        OutputGenerator outputGenerator = new OutputGenerator();
//        outputGenerator.generateSymbolFile();
//        outputGenerator.terminal.showAddressFile();
//        outputGenerator.terminal.showSymbolFile();
//
//        ObjectCodeGenerator objectCodeGenerator = new ObjectCodeGenerator();
//        objectCodeGenerator.generate();
//        objectCodeGenerator.terminal.print();
//
//        FileWriter fileWriter = new FileWriter(outputGenerator);
//        fileWriter.writeAddressFile();
//        fileWriter.writeSymbolFile();
//        fileWriter.writeObjectCodeFile(objectCodeGenerator);
    }
}
