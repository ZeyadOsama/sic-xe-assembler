package assembler.structure;

import org.jetbrains.annotations.Nullable;

public class Instruction {

    private String label;
    private String mnemonic;
    private String firstOperand;
    private String secondOperand;
    private String comment;

    public Instruction() {
    }

    public Instruction(@Nullable String label, @Nullable String mnemonic,
                       @Nullable String firstOperand, @Nullable String secondOperand,
                       @Nullable String comment) {
        setLabel(label);
        setMnemonic(mnemonic);
        setFirstOperand(firstOperand);
        setSecondOperand(secondOperand);
        this.comment = comment;
    }

    @Nullable
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label != null)
            this.label = label.toUpperCase();
    }

    @Nullable
    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        if (mnemonic != null)
            this.mnemonic = mnemonic.toUpperCase();
    }

    @Nullable
    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        if (firstOperand != null)
            this.firstOperand = firstOperand.toUpperCase();
    }

    @Nullable
    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        if (secondOperand != null)
            this.secondOperand = secondOperand.toUpperCase();
    }

    @Nullable
    public String getComment() {
        return comment;
    }

    public boolean hasLabel() {
        return label != null;
    }

    public boolean hasMnemonic() {
        return mnemonic != null;
    }

    public boolean hasFirstOperand() {
        return firstOperand != null;
    }

    public boolean hasSecondOperand() {
        return secondOperand != null;
    }

    public boolean hasComment() {
        return comment != null;
    }
}
