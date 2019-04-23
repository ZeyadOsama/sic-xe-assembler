package main;

import assembler.core.PassOne;
import io.FileLoader;

public class Main {

    public static void main(String[] args) {
        new FileLoader().loadFile();
        PassOne.start();
    }
}
