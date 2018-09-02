package ch.rs.IssueReported.tools.text;

public enum DetailLevel {

    DETAILED{
        public String getDetails(StackTraceElement ste) {
            return new StringBuilder()
                    .append(FUNCTION_NAME)
                    .append(ste.getMethodName())
                    .append(NEWLINE)
                    .append(AT_LINE)
                    .append(ste.getLineNumber())
                    .append(NEWLINE)
                    .append(IS_NATIVE)
                    .append(ste.isNativeMethod())
                    .append(NEWLINE)
                    .append(CLASS_NAME)
                    .append(ste.getClassName())
                    .append(NEWLINE)
                    .append(FILE_NAME)
                    .append(ste.getFileName())
                    .append(NEWLINE)
                    .append(TITLE_FORMATTING)
                    .append(NEWLINE)
                    .toString();
        }
    },

    MEDIUM{
        public String getDetails(StackTraceElement ste) {
            return new StringBuilder()
                    .append(FUNCTION_NAME)
                    .append(ste.getMethodName())
                    .append(NEWLINE)
                    .append(AT_LINE)
                    .append(ste.getLineNumber())
                    .append(NEWLINE)
                    .append(CLASS_NAME)
                    .append(ste.getClassName())
                    .append(NEWLINE)
                    .append(FILE_NAME)
                    .append(ste.getFileName())
                    .append(NEWLINE)
                    .append(TITLE_FORMATTING)
                    .append(NEWLINE)
                    .toString();
        }
    },

    LOW{
        public String getDetails(StackTraceElement ste) {
            return new StringBuilder()
                    .append(FUNCTION_NAME)
                    .append(ste.getMethodName())
                    .append(NEWLINE)
                    .append(AT_LINE)
                    .append(ste.getLineNumber())
                    .append(NEWLINE)
                    .append(CLASS_NAME)
                    .append(ste.getClassName())
                    .append(NEWLINE)
                    .append(TITLE_FORMATTING)
                    .append(NEWLINE)
                    .toString();
        }
    },

    CRITICAL {
        public String getDetails(StackTraceElement ste) {
            return new StringBuilder()
                    .append(FUNCTION_NAME)
                    .append(ste.getMethodName())
                    .append(NEWLINE)
                    .append(CLASS_NAME)
                    .append(ste.getClassName())
                    .append(NEWLINE)
                    .append(TITLE_FORMATTING)
                    .append(NEWLINE)
                    .toString();
        }
    };

    private static final String FUNCTION_NAME = "Function name: ";
    private static final String IS_NATIVE = "Is function native: ";
    private static final String CLASS_NAME = "Class name: ";
    private static final String FILE_NAME = "File name: ";
    private static final String AT_LINE = "Error occurred on line: ";
    private static final String TITLE_FORMATTING = "##";
    private static final String NEWLINE = System.lineSeparator();

    public abstract String getDetails(StackTraceElement ste);


}
