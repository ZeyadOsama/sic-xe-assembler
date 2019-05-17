package misc.utils;

import misc.constants.Constants;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

import static misc.utils.Validations.Operand.*;

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
     * @param operand to be parsed
     * @return operand value without any special symbols
     */
    @NotNull
    public static String removeSpecialSymbol(@NotNull String operand) {
        if (isIndirect(operand))
            return operand.replace("@", "");
        if (isImmediate(operand))
            return operand.replace("#", "");
        if (isLiteral(operand))
            return parseDataOperand(operand);
        return operand;
    }

    /**
     * @param string to be extended
     * @param length to be extended
     * @return extended String
     */
    public static String extendLength(String string, int length) {
        return ("0".repeat(Math.max(0, length)) + string).substring(string.length());
    }

    /**
     * @param string to be extended
     * @param length to be extended
     * @return extended String
     */
    public static String extendSpaces(String string, int length) {
        return ((Constants.SPACE).repeat(Math.max(0, length - string.length())) + string).substring(string.length());
    }

    /**
     * @param string to be reversed
     * @return reversed string
     */
    public static String reverseWords(String string) {
        String[] stringChars = string.split(" ");
        StringBuilder builder = new StringBuilder();
        for (int i = stringChars.length - 1; i >= 0; i--)
            builder.append(stringChars[i]).append(Constants.SPACE);
        return builder.toString().substring(0, builder.length() - 1);
    }

    /**
     * @param operand data Operand  C'EOF' || X'F1'
     * @return its integer value
     */
    public static String parseDataOperand(String operand) {
        StringBuilder parsedOperand = new StringBuilder();

        if (operand.startsWith("X"))
            parsedOperand.append(operand.substring(1).replace("'", ""));

        else if (operand.startsWith("C"))
            for (int i = 2; i < operand.length() - 1; i++)
                parsedOperand.append(Integer.toHexString(operand.charAt(i)));

        else if (Pattern.matches("0x[0-9A-F]+", operand))
            parsedOperand.append(operand.substring(2));

        return parsedOperand.toString();
    }
}
