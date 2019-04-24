package assembler.structure;

import assembler.constants.Format;
import assembler.constants.OperandType;
import org.jetbrains.annotations.Nullable;

public class Operation {

    private final OperandType firstOperand;
    private final OperandType secondOperand;
    private Format format;
    private String name;
    private String opcode;

    public Operation(String name, String opcode, Format format, OperandType firstOperand, OperandType secondOperand) {
        setName(name);
        setOpcode(opcode);
        this.format = format;
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
    }

    @Nullable
    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name.toUpperCase();
    }

    @Nullable
    public String getOpcode() {
        return opcode;
    }

    private void setOpcode(String opcode) {
        this.opcode = opcode.toUpperCase();
    }

    @Nullable
    public Format getFormat() {
        return format;
    }

    @Nullable
    public OperandType getFirstOperand() {
        return firstOperand;
    }

    @Nullable
    public OperandType getSecondOperand() {
        return secondOperand;
    }

    public boolean hasOperand() {
        return hasFirstOperand() || hasSecondOperand();
    }

    public boolean hasFirstOperand() {
        return firstOperand != OperandType.NONE;
    }

    public boolean hasSecondOperand() {
        return secondOperand != OperandType.NONE;
    }
}
