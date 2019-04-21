package assembler.structure;

public class Symbol {

    private String address;
    private String label;
    private int length;
    private boolean relocatable;

    public Symbol() {
    }

    public Symbol(String address, String label, int length, boolean relocatable) {
        setAddress(address);
        setLabel(label);
        this.length = length;
        this.relocatable = relocatable;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address.toUpperCase();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label.toUpperCase();
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean isRelocatable() {
        return relocatable;
    }

    public void setRelocatable(boolean relocatable) {
        this.relocatable = relocatable;
    }
}
