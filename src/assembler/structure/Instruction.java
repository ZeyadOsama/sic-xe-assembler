package assembler.structure;

public class Instruction {

    private String label;
    private String mnemonic;
    private String firstOperand;
    private String secondOperand;
    private String comment;

    public Instruction(String label, String mnemonic, String firstOperand, String secondOperand, String comment) {
        setLabel(label);
        setMnemonic(mnemonic);
        setFirstOperand(firstOperand);
        setSecondOperand(secondOperand);
        this.comment = comment;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label != null)
            this.label = label.toUpperCase();
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        if (mnemonic != null)
            this.mnemonic = mnemonic.toUpperCase();
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        if (firstOperand != null)
            this.firstOperand = firstOperand.toUpperCase();
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        if (secondOperand != null)
            this.secondOperand = secondOperand.toUpperCase();
    }

    public Boolean hasLabel() {
        return label != null;
    }

    public String getComment() {
        return comment;
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
