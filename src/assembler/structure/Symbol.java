package assembler.structure;

public class Symbol {

    private String label;
    private int address;

    public Symbol(String label, int address) {
        this.label = label;
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public int getAddress() {
        return address;
    }
}
