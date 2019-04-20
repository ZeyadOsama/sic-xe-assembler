package assembler.structure;

public class Instruction {

    private String label;
    private String operationCode;
    private String operand;

    public Instruction() {
    }

    public Instruction(String operationCode, String operand) {
        setOperationCode(operationCode);
        setOperand(operand);
    }

    public Instruction(String label, String operationCode, String operand) {
        setLabel(label);
        setOperationCode(operationCode);
        setOperand(operand);
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

    public String getOperand() {
        return operand;
    }

    public void setOperand(String operand) {
        this.operand = operand.toUpperCase();
    }
}
