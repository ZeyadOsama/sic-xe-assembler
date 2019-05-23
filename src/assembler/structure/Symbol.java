package assembler.structure;

import org.jetbrains.annotations.NotNull;

public final class Symbol {

    private String label;
    private int address;
    private String value;

    public Symbol(@NotNull String label, int address, @NotNull String value) {
        this.label = label;
        this.address = address;
        this.value = value;
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    public int getAddress() {
        return address;
    }

    @NotNull
    public String getValue() {
        return value;
    }
}
