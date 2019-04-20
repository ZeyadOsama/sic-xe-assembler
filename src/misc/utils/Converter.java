package misc.utils;

import org.jetbrains.annotations.NotNull;

public final class Converter {

    @NotNull
    public static String toBinary(int hexNumber) {
        return Integer.toBinaryString(hexNumber);
    }

    @NotNull
    public static String toBinary(@NotNull String hexNumber) {
        return Integer.toBinaryString(Integer.parseInt(hexNumber));
    }

    @NotNull
    public static String toHex(int decNumber) {
        return Integer.toHexString(decNumber).toUpperCase();
    }

    @NotNull
    public static String toHex(@NotNull String decNumber) {
        return Integer.toHexString(Integer.parseInt(decNumber)).toUpperCase();
    }
}
