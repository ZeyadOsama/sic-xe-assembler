package assembler.structure;

public class Register {

    private final int address;
    private int value;

    public Register(int address) {
        this.address = address;
    }

    public int getAddress() {
        return address;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
