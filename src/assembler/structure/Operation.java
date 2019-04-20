package assembler.structure;

import misc.constants.Format;

public class Operation {

    private String operationName;
    private String operationCode;
    private Format format;

    public Operation() {
    }

    public Operation(String operationName, String operationCode, Format format) {
        setOperationName(operationName);
        setOperationCode(operationCode);
        setFormat(format);
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName.toUpperCase();
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode.toUpperCase();
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }
}
