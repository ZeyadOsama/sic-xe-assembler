package main;

import assembler.structure.Instruction;
import io.FileLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new FileLoader().loadFile();
        } catch (IOException e) {
            e.printStackTrace();

        }

        for (Instruction i : FileLoader.x) {
            System.out.println(i.getLabel());
            System.out.println(i.getMnemonic());
            System.out.println(i.getFirstOperand());
            System.out.println(i.getSecondOperand());
            System.out.println();
        }
    }
}
