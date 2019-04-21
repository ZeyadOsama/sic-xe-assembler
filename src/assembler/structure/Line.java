package assembler.structure;

public class Line {

    private String label;
    private String operationCode;
    private String firstOperand;
    private String secondOperand;

    public Line(String label, String operationCode, String firstOperand, String secondOperand) {
        setLabel(label);
        setOperationCode(operationCode);
        setFirstOperand(firstOperand);
        setSecondOperand(secondOperand);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label.toUpperCase();
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode.toUpperCase();
    }

    public String getFirstOperand() {
        return firstOperand;
    }

    public void setFirstOperand(String firstOperand) {
        this.firstOperand = firstOperand;
    }

    public String getSecondOperand() {
        return secondOperand;
    }

    public void setSecondOperand(String secondOperand) {
        this.secondOperand = secondOperand;
    }
}
