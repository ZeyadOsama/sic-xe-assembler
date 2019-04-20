package main;

import io.Loader;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new Loader().loadFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
