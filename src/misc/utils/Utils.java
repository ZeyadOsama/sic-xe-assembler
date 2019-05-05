package misc.utils;

import java.util.regex.Pattern;

public final class Utils {

    private Utils() {
    }

    public static String addHexadecimalNotation(String string) {
        return "0x" + string;
    }

    public static String removeHexadecimalNotation(String string) {
        return string.replace("0x", "");
    }

    /**
     * @param string to be extended
     * @param length to be extended
     * @return extended String
     */
    public static String extendLength(String string, int length) {
        StringBuilder zeroes = new StringBuilder();
        for (int i = 0; i < length; i++) zeroes.append("0");
        return (zeroes.toString() + string).substring(string.length());
    }

    /**
     * @param string to be extended
     * @param length to be extended
     * @return extended String
     */
    public static String extendSpaces(String string, int length) {
        StringBuilder zeroes = new StringBuilder();
        for (int i = string.length(); i < length; i++) zeroes.append(" ");
        return (zeroes.toString() + string).substring(string.length());
    }

    /**
     * @param operand data Operand  C'EOF' || X'F1'
     * @return its integer value
     */
    public static String parseDataOperand(String operand) {
        String parsedOperand = "";
        if (operand.startsWith("X"))
            parsedOperand = operand.substring(1).replace("'", "");

        else if (operand.startsWith("C"))
            for (int i = 2; i < operand.length() - 1; i++)
                parsedOperand += Integer.toHexString(operand.charAt(i));

        else if (Pattern.matches("0x[0-9A-F]+", operand))
            parsedOperand = operand.substring(2);

        return String.valueOf(Integer.parseInt(parsedOperand, 16));
    }
}
