package assembler.structure;

import java.util.HashMap;

public class OperationTable {

    private static HashMap<String, Operation> operationTable = new HashMap<>();

    private OperationTable() {
    }

    public static OperationTable getInstance() {
        return instance;
    }

    private static OperationTable instance = new OperationTable();

    public void fillTable() {

    }

    public HashMap<String, Operation> getTable() {
        return operationTable;
    }

    public Operation getOperation(String operationName) {
        return operationTable.get(operationName);
    }
}
