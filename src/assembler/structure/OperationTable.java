package assembler.structure;

import java.util.HashMap;

public class OperationTable {


    private static HashMap<String, Operation> operationTable = new HashMap<>();
    private static OperationTable instance = new OperationTable();

    private OperationTable() {
    }

    public static OperationTable getInstance() {
        return instance;
    }

    public void fillTable() {

    }

    public HashMap<String, Operation> getTable() {
        return operationTable;
    }

    public Operation getOperation(String operationName) {
        return operationTable.get(operationName);
    }
}
