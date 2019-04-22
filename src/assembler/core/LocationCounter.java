package assembler.core;

import assembler.constants.Format;

import java.util.ArrayList;

public class LocationCounter {

    private static LocationCounter instance = new LocationCounter();

    private LocationCounter() {
    }

    public LocationCounter getInstance() {
        return instance;
    }

    private static String currentAddress = "0";
    private static ArrayList<String> addresses = new ArrayList<>();

    public static void set(String address) {
        LocationCounter.currentAddress = address;
    }

    public static String get() {
        return currentAddress;
    }

    public static void reset() {
        currentAddress = "0";
    }

    // TODO
    public static String increment(Format format) {
        switch (format) {
            case ONE:

            case TWO:

            case THREE:

            case FOUR:

        }
        return null;
    }

    public static ArrayList<String> getAddresses() {
        return addresses;
    }

    public static void addAddress(String address) {
        LocationCounter.currentAddress = address;
        LocationCounter.addresses.add(address);
    }
}
