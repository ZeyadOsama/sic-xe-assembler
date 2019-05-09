package assembler.core;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import misc.exceptions.FormatException;
import misc.utils.Utils;
import misc.utils.Validations;

import java.util.ArrayList;
import java.util.Objects;

import static misc.utils.Converter.Decimal;
import static misc.utils.Converter.Hexadecimal;
import static misc.utils.Validations.isDirective;
import static misc.utils.Validations.isOperation;

public final class LocationCounter {

    /**
     * Singleton class
     */
    private static LocationCounter instance = new LocationCounter();

    public static LocationCounter getInstance() {
        return instance;
    }

    private LocationCounter() {
    }

    private final int WORD_LENGTH = 3;

    private boolean enabled = true;
    private int previousAddress = 0;
    private int currentAddress = 0;
    private int programCounter = 0;
    private ArrayList<Integer> addresses = new ArrayList<>();
    private ArrayList<String> convertedAddresses = new ArrayList<>();

    public void set(int address) {
        currentAddress = address;
    }

    public void reset() {
        currentAddress = 0;
        programCounter = 0;
    }

    public void update(Instruction instruction) throws FormatException {
        if (!enabled)
            return;

        String mnemonic = instruction.getMnemonic();
        if (mnemonic == null)
            return;

        if (isDirective(mnemonic)) {
            switch (DirectiveTable.getDirective(mnemonic).getLength()) {
                case NONE:
                    switch (mnemonic) {
                        case DirectiveTable.START:
                            currentAddress = Hexadecimal.toDecimal(instruction.getFirstOperand());
                            addAddress(currentAddress);
                            previousAddress = currentAddress;
                            Program.setName(instruction.getLabel());
                            Program.setStartAddress(instruction.getFirstOperand());
                            return;
                        case DirectiveTable.EQU:
                            break;
                    }
                    break;
                case ONE:
                    currentAddress += 1;
                    break;
                case TWO:
                    currentAddress += 2;
                    break;
                case THREE:
                    currentAddress += 3;
                    break;
                case VARIABLE:
                    switch (mnemonic) {
                        case DirectiveTable.RESW:
                            currentAddress +=
                                    (Integer.parseInt(Objects.requireNonNull(instruction.getFirstOperand()))
                                            * WORD_LENGTH);
                            break;
                        case DirectiveTable.RESB:
                            currentAddress +=
                                    (Integer.parseInt(Objects.requireNonNull(instruction.getFirstOperand())));
                            break;
                        case DirectiveTable.BYTE:
                            if (Validations.Operand.isLiteral(instruction.getFirstOperand()))
                                currentAddress += (instruction.getFirstOperand().length() - 3);
                            else
                                currentAddress += 1;
                            break;
                    }
                    break;
            }
            update();
        } else if (isOperation(mnemonic)) {
            switch (Objects.requireNonNull(OperationTable.getOperation(mnemonic).getFormat())) {
                case ONE:
                    currentAddress += 1;
                case TWO:
                    currentAddress += 2;
                    break;
                case THREE:
                    currentAddress += 3;
                    break;
                case FOUR:
                    currentAddress += 4;
                    break;
            }
            update();
        } else
            enabled = false;

        if (programCounter == Program.getProgramCount())
            if (instruction.getMnemonic().equals(DirectiveTable.END)) {
                if (instruction.hasLabel()) {
                    ErrorHandler errorHandler = ErrorHandler.getInstance();
                    errorHandler.setHasError(true);
                    errorHandler.setCurrentError(ErrorHandler.CAN_NOT_HAVE_LABEL);
                }
            } else {
                ErrorHandler errorHandler = ErrorHandler.getInstance();
                errorHandler.setHasError(true);
                errorHandler.setCurrentError(ErrorHandler.MISSING_END);
            }
    }


    public void update() {
        addAddress(previousAddress);
        previousAddress = currentAddress;
    }

    public void addAddress(int address) {
        programCounter++;
        addresses.add(address);
        convertedAddresses
                .add(Utils.addHexadecimalNotation(
                        Utils.extendLength(
                                Decimal.toHexadecimal(address), 4).toUpperCase()));
    }

    public ArrayList<Integer> getAddresses() {
        return addresses;
    }

    public ArrayList<String> getHexAddresses() {
        return convertedAddresses;
    }

    public int getProgramCounter() {
        return programCounter;
    }

    public int getCurrentAddress() {
        return addresses.get(programCounter - 1);
    }

    public String getCurrentConvertedAddress() {
        return convertedAddresses.get(programCounter - 1);
    }
}
