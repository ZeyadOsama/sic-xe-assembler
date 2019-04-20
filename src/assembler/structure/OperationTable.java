package assembler.structure;

import misc.constants.Format;

import java.util.HashMap;

public class OperationTable {

    private static HashMap<String, Operation> operationTable = new HashMap<>();

    public static OperationTable getInstance() {
        return instance;
    }

    private OperationTable() {
    }

    private static OperationTable instance = new OperationTable();

    public Operation getOperation(String operationName) {
        return operationTable.get(operationName);
    }

    public HashMap<String, Operation> get() {
        return operationTable;
    }

    public void add(String operationName, Operation operation) {
        operationTable.put(operationName, operation);
    }

    public void add(String operationName, String operationCode, Format format) {
        operationTable.put(operationName, new Operation(operationName, operationCode, format));
    }
}
