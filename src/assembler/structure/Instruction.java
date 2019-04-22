package assembler.structure;

public class Instruction {

    private String label;
    private String mnemonic;
    private String firstOperand;
    private String secondOperand;

    public Instruction(String label, String mnemonic, String firstOperand, String secondOperand) {
        setLabel(label);
        setMnemonic(mnemonic);
        setFirstOperand(firstOperand);
        setSecondOperand(secondOperand);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label.toUpperCase();
    }

    public String getMnemonic() {
        return mnemonic;
    }

    public void setMnemonic(String mnemonic) {
        this.mnemonic = mnemonic.toUpperCase();
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand.toUpperCase();
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand.toUpperCase();
    }

    public Boolean hasLabel() {
        return label != null;
    }

    public boolean hasFirstOperand() {
        return firstOperand != null;
    }

    public boolean hasSecondOperand() {
        return secondOperand != null;
    }
}
