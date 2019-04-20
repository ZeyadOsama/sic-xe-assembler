package assembler.structure;

public class Operation {

    private String operationName;
    private String operationCode;
    private short format;

    public Operation() {
    }

    public Operation(String operationName, String operationCode, short format) {
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

    public short getFormat() {
        return format;
    }

    public void setFormat(short format) {
        if (format != 2 && format != 3) throw new FormatException();
        else this.format = format;
    }

    /**
     * Exception class for unexpected input errors
     */
    public class FormatException extends RuntimeException {
        /**
         * Construct this exception object.
         */
        public FormatException() {
            super();
        }

        /**
         * Construct this exception object.
         *
         * @param message the error message.
         */
        public FormatException(String message) {
            super(message);
        }
    }
}
