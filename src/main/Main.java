package main;

import io.FileLoader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new FileLoader().loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
