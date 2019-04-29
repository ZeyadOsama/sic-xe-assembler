package assembler.structure;

import assembler.constants.Format;
import assembler.constants.OperandType;
import org.jetbrains.annotations.Nullable;

public class Operation {

    private final OperandType firstOperandType;
    private final OperandType secondOperandType;
    private Format format;
    private String name;
    private int opcode;
    private boolean indexable;

    public Operation(String name, int opcode, Format format,
                     OperandType firstOperandType, OperandType secondOperandType) {
        setName(name);
        this.opcode = opcode;
        this.format = format;
        this.firstOperandType = firstOperandType;
        this.secondOperandType = secondOperandType;
        indexable = format == Format.THREE;
    }

    @Nullable
    public String getName() {
        return name;
    }

    private void setName(String name) {
        if (name != null)
            this.name = name.toUpperCase();
    }

    public int getOpcode() {
        return opcode;
    }

    public Format getFormat() {
        return format;
    }

    public OperandType getFirstOperandType() {
        return firstOperandType;
    }

    public OperandType getSecondOperandType() {
        return secondOperandType;
    }

    public boolean hasOperand() {
        return hasFirstOperand() || hasSecondOperand();
    }

    public boolean hasFirstOperand() {
        return firstOperandType == OperandType.DONT_CARE || firstOperandType != OperandType.NONE;
    }

    public boolean hasSecondOperand() {
        return secondOperandType == OperandType.DONT_CARE || secondOperandType != OperandType.NONE;
    }

    public boolean isIndexable() {
        return indexable;
    }
}
