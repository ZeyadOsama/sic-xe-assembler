package misc.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Utility class to convert numbers from one form to another
 */
public final class Converter {

    private Converter() {
    }

    private final static int RADIX_HEX = 16;
    private final static int RADIX_BIN = 2;

    public final static class Decimal {

        @NotNull
        public static String toBinary(int decimal) {
            return Integer.toBinaryString(decimal);
        }

        @NotNull
        public static String toBinary(@NotNull String number) {
            return Integer.toBinaryString(Integer.parseInt(number));
        }

        @NotNull
        public static String toHexadecimal(int number) {
            return Integer.toHexString(number).toUpperCase();
        }

        @NotNull
        public static String toHexadecimal(@NotNull String number) {
            return Integer.toHexString(Integer.parseInt(number)).toUpperCase();
        }
    }

    public final static class Hexadecimal {

        public static int toDecimal(String hexadecimal) {
            return Integer.parseInt(hexadecimal, RADIX_HEX);
        }

        @NotNull
        public static String toBinary(@NotNull String hexadecimal) {
            return Decimal.toBinary(toDecimal(hexadecimal));
        }
    }

    public final static class Binary {

        public static int toDecimal(@NotNull String binary) {
            return Integer.parseInt(binary, RADIX_BIN);
        }

        @NotNull
        public static String toHexadecimal(@NotNull String binary) {
            return Decimal.toHexadecimal(toDecimal(binary));
        }
    }
}
