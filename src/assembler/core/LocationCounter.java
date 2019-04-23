package assembler.core;

import assembler.structure.Instruction;
import assembler.tables.DirectiveTable;
import assembler.tables.OperationTable;
import misc.exceptions.FormatException;
import misc.exceptions.ParsingException;
import misc.utils.Converter;
import misc.utils.Utils;

import java.util.ArrayList;

import static misc.utils.Validations.isDirective;
import static misc.utils.Validations.isOperation;

public final class LocationCounter {

    private static LocationCounter instance = new LocationCounter();

    private LocationCounter() {
    }

    public static LocationCounter getInstance() {
        return instance;
    }

    private final int WORD_LENGTH = 3;

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
        String mnemonic = instruction.getMnemonic();
        if (mnemonic == null)
            return;

        if (mnemonic.equals(DirectiveTable.START)) {

            currentAddress = Integer.parseInt(instruction.getFirstOperand());
            addAddress(currentAddress);
            previousAddress = currentAddress;
            return;
        }

        if (isDirective(mnemonic)) {
            switch (DirectiveTable.getDirective(mnemonic).getLength()) {
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
                    if (mnemonic.equals(DirectiveTable.RESW))
                        currentAddress += (Integer.parseInt(instruction.getFirstOperand()) * WORD_LENGTH);
                    else currentAddress += (Integer.parseInt(instruction.getFirstOperand()));
                    break;
            }
            addAddress(previousAddress);
            previousAddress = currentAddress;
        } else if (isOperation(mnemonic)) {
            switch (OperationTable.getOperation(mnemonic).getFormat()) {
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
            addAddress(previousAddress);
            previousAddress = currentAddress;
        } else throw new ParsingException("Could not find specific format", programCounter);
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
                                Converter.Decimal.toHexadecimal(address), 4).toUpperCase()));
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

    public String getLastAddress() {
        return convertedAddresses.get(programCounter - 1);
    }
}
