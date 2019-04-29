package assembler.core;

import misc.utils.Converter;
import parser.Parser;

import static misc.utils.Utils.extendLength;
import static misc.utils.Utils.removeHexadecimalNotation;

public class Program {

    private static String name;
    private static String startAddress;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Program.name = name;
    }

    public static String getStartAddress() {
        return startAddress;
    }

    public static void setStartAddress(String startAddress) {
        Program.startAddress = extendLength(removeHexadecimalNotation(startAddress),6);
    }

    public static String getEndAddress() {
        return extendLength(removeHexadecimalNotation(
                LocationCounter.getInstance().getHexAddresses().get(getInstructionCount() - 1)),6);
    }

    public static int getInstructionCount() {
        return LocationCounter.getInstance().getProgramCounter();
    }

    public static boolean hasBaseDirective() {
        return Parser.getInstance().hasBaseDirective();
    }

    public static String getObjectCodeLength() {
        int startAddress = Converter.Hexadecimal.toDecimal(getStartAddress());
        int endAddress = Converter.Hexadecimal.toDecimal(getEndAddress());
        return extendLength(Converter.Decimal.toHexadecimal(endAddress - startAddress),6);
    }
}
