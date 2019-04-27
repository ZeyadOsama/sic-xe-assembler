package assembler.structure;

import assembler.constants.Format;
import assembler.constants.OperandType;
import org.jetbrains.annotations.Nullable;

public class Operation {

    private final OperandType firstOperandType;
    private final OperandType secondOperandType;
    private Format format;
    private String name;
    private String opcode;

    public Operation(String name, String opcode, Format format,
                     OperandType firstOperandType, OperandType secondOperandType) {
        setName(name);
        setOpcode(opcode);
        this.format = format;
        this.firstOperandType = firstOperandType;
        this.secondOperandType = secondOperandType;
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
    public OperandType getFirstOperandType() {
        return firstOperandType;
    }

    @Nullable
    public OperandType getSecondOperandType() {
        return secondOperandType;
    }

    public boolean hasOperand() {
        return hasFirstOperand() || hasSecondOperand();
    }

    public boolean hasFirstOperand() {
        return firstOperandType != OperandType.NONE;
    }

    public boolean hasSecondOperand() {
        return secondOperandType != OperandType.NONE;
    }
}
