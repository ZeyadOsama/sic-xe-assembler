package assembler.structure;

import org.jetbrains.annotations.NotNull;

public final class Symbol {

    private String label;
    private int address;

    public Symbol(@NotNull String label, int address) {
        this.label = label;
        this.address = address;
    }

    @NotNull
    public String getLabel() {
        return label;
    }

    public int getAddress() {
        return address;
    }
}
