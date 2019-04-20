package assembler.core;

public class LocationCounter {

    private static LocationCounter instance = new LocationCounter();

    private LocationCounter() {
    }

    public LocationCounter getInstance() {
        return instance;
    }
}
