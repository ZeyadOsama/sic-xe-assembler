package assembler.core;

import assembler.constants.Format;

public class LocationCounter {

    private static LocationCounter instance = new LocationCounter();

    private LocationCounter() {
    }

    public LocationCounter getInstance() {
        return instance;
    }

    private static int counter = 0;

    public static int get() {
        return counter;
    }

    public static void reset() {
        counter = 0;
    }

    // TODO
    public static int increment(Format format) {
        switch (format) {
            case ONE:
                return counter;
            case TWO:

            case THREE:
                return counter += 3;
            case FOUR:
                return counter += 4;
        }
        return counter;
    }
}
