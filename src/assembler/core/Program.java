package assembler.core;

import misc.utils.Converter;
import parser.Parser;

import java.util.ArrayList;

import static misc.utils.Utils.extendLength;
import static misc.utils.Utils.removeHexadecimalNotation;


/**
 * Responsible for having all details of current read program.
 */
public final class Program {

    private static String name;
    private static String startAddress;
    private static ArrayList<String> instructionsList = new ArrayList<>();

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
        Program.startAddress = extendLength(removeHexadecimalNotation(startAddress), 6);
    }

    public static String getEndAddress() {
        return extendLength(removeHexadecimalNotation(
                LocationCounter.getInstance().getHexAddresses().get(
                        LocationCounter.getInstance().getHexAddresses().size() - 1)), 6);
    }

    public static boolean hasBaseDirective() {
        return Parser.getInstance().hasBaseDirective();
    }

    public static String getObjectCodeLength() {
        int startAddress = Converter.Hexadecimal.toDecimal(getStartAddress());
        int endAddress = Converter.Hexadecimal.toDecimal(getEndAddress());
        return extendLength(Converter.Decimal.toHexadecimal(endAddress - startAddress), 6);
    }

    /**
     * @return ArrayList containing all instructions of the given program in
     * it's original form {String}
     */
    public static ArrayList<String> getInstructionsList() {
        return instructionsList;
    }

    public static int getProgramCount() {
        return instructionsList.size();
    }

    public static boolean hasError() {
        return ErrorHandler.getInstance().isNonExecutable();
    }
}
