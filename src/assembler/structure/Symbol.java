package assembler.structure;

public class Symbol {

    private String address;
    private String label;
    private String value;

    public Symbol() {
    }

    public Symbol(String address, String label, String value) {
        this.address = address;
        this.label = label;
        this.value = value;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean hasValue() {
        return value != null;
    }
}
