package assembler.tables;

import assembler.structure.Register;

import java.util.HashMap;

public class RegisterTable {

    public static final String A = "A";
    public static final String X = "X";
    public static final String L = "L";
    public static final String B = "B";
    public static final String S = "S";
    public static final String T = "T";
    public static final String F = "F";
    public static final String PC = "PC";
    public static final String SW = "SW";

    private static HashMap<String, Register> registers = new HashMap<>();

    static {
        load();
    }

    public static String getRegisterName(int registerNumber) {
        switch (registerNumber) {
            case 0:
                return "A";
            case 1:
                return "X";
            case 2:
                return "L";
            case 3:
                return "B";
            case 4:
                return "S";
            case 5:
                return "T";
            case 6:
                return "F";
            case 8:
                return "PC";
            case 9:
                return "SW";
        }
        return null;
    }

    private RegisterTable() {
    }

    public static Register getRegister(String registerName){
        return registers.get(registerName);
    }

    public static boolean contains(String register) {
        return registers.containsKey(register);
    }

    /**
     * Load all register names and values to details to hash map.
     */
    private static void load() {
        registers.put("A", new Register(0));
        registers.put("X", new Register(1));
        registers.put("L", new Register(2));
        registers.put("B", new Register(3));
        registers.put("S", new Register(4));
        registers.put("T", new Register(5));
        registers.put("F", new Register(6));
        registers.put("PC", new Register(8));
        registers.put("SW", new Register(9));
    }
}
