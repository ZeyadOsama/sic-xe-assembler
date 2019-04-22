package assembler.tables;

public class RegisterTable {

    public static int getRegisterNumber(String registerName) {
        if (registerName.equals("A"))
            return 0;
        if (registerName.equals("X"))
            return 1;
        if (registerName.equals("L"))
            return 2;
        if (registerName.equals("B"))
            return 3;
        if (registerName.equals("S"))
            return 4;
        if (registerName.equals("T"))
            return 5;
        if (registerName.equals("F"))
            return 6;
        if (registerName.equals("PC"))
            return 8;
        if (registerName.equals("SW"))
            return 9;

        return -1;
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
}