package ch.hephaistos.utilities.apollon.reporting.generation.text;

public enum CensoringLevel {

    /**
     * Returns the Cause of the Exception as a String
     */
    NORMAL,

    /**
     * Returns a censored String of the cause. This is useful when handling stuff like
     * filenames, paths, credentials.
     */
    CENSORED {

        @Override
        public String getCausingObject(Exception e){
            return "|||||CENSORED|||||";
        }

    };

    public String getCausingObject(Exception e){
        return e.getMessage();
    };


}
