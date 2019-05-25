package misc.utils;

/**
 * Utility class to print files content in terminal
 */
public abstract class Terminal {

    public static void headerMessage(String message) {
        System.out.println(ConsoleColors.PURPLE + message + ConsoleColors.RESET);
    }

    public static void acceptMessage(String message) {
        System.out.println(ConsoleColors.GREEN + message + ConsoleColors.RESET);
    }

    public static void errorMessage(String message) {
        System.out.println(ConsoleColors.RED + message + ConsoleColors.RESET);
    }

    public abstract void print();
}
