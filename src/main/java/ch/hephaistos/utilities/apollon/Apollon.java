package ch.hephaistos.utilities.apollon;


import ch.hephaistos.utilities.apollon.reporting.ReportingUnit;
import ch.hephaistos.utilities.apollon.reporting.generation.text.CensoringLevel;
import ch.hephaistos.utilities.apollon.reporting.generation.text.DetailLevel;

import java.io.IOException;

public class Apollon implements Thread.UncaughtExceptionHandler {

    protected static final String STANDARD_BRANCH = "master";
    private String OAuthKey;
    private String repoName;
    private String branch;
    private ReportingUnit reportingUnit;

    private DetailLevel detailLevel;
    private CensoringLevel censoringLevel;


    public Apollon(String OAuthKey, String repoOwner, String repoName) {
        this(OAuthKey, repoOwner, repoName, STANDARD_BRANCH);
    }

    /**
     * This constructor initializes a {@link ReportingUnit} with the given data.
     * For the case that something does not work (IOException), Apollon sets the reporting unit to
     * null and reports this to System.err.out(). It will then check for null every time you try
     * to report an exception - and if {@link ReportingUnit} is null, do nothing and report using
     * System.err.out();
     *
     * This will also initialize the {@link DetailLevel} to {@link DetailLevel#MEDIUM} and
     * {@link CensoringLevel} to {@link CensoringLevel#CENSORED}. Check the corresponding documentation
     * for further information.
     *
     * To check if Apollon was initialized correctly us {@link #isApollonInitialized()}.
     *
     * @param OAuthKey this is the OAuthKey for the bot. It will use this to post issues. Make sure this OAuthKey
     *                 has access to the targeted Repository!
     * @param repoOwner the name of the owner of the repository you want to target
     * @param repoName the name of the repository itself
     * @param branch the branch you want to use. If not set, the branch will default to {@value #STANDARD_BRANCH}.
     */
    public Apollon(String OAuthKey, String repoOwner, String repoName, String branch) {
        try {
            reportingUnit = new ReportingUnit(OAuthKey, repoOwner, repoName, branch);
            detailLevel = DetailLevel.MEDIUM;
            censoringLevel = CensoringLevel.CENSORED;
        } catch (IOException ioe) {
            reportingUnit = null;
            System.err.println("Failed to create the reporting unit: " + ioe.toString());
        }
    }

    /**
     * This function allows you to dynamically supply exceptions to report to the repository.
     * The exception will be reported according to the set {@link DetailLevel DetailLevel}
     * and {@link CensoringLevel}. If you want to
     * supply a different kind of {@link DetailLevel DetailLevel}
     * or {@link CensoringLevel CensoringLevel} use {@link #reportException(Exception, DetailLevel, CensoringLevel)}
     *
     * @param exception the thrown exception to be reported
     */
    public void reportException(Exception exception) {
        if(isApollonInitialized()){
            reportExceptionAsIssue(exception, detailLevel, censoringLevel);
        } else {
            System.err.println("Apollon has not been initialized correctly!");
        }
    }

    /**
     * This function allows you to dynamically supply exceptions to report to the repository.
     * The exception will be reported according to the given {@link DetailLevel DetailLevel}
     * and {@link CensoringLevel}. If you want to use the set options, use {@link #reportException(Exception)}
     *
     * @param exception      the thrown exception to be reported
     * @param detailLevel    the {@link DetailLevel} of the report
     * @param censoringLevel the {@link CensoringLevel} of the report
     */
    public void reportException(Exception exception, DetailLevel detailLevel,
                                             CensoringLevel censoringLevel) {
        if(isApollonInitialized()){
            reportExceptionAsIssue(exception, detailLevel, censoringLevel);
        } else {
            System.err.println("Apollon has not been initialized correctly!");
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(isApollonInitialized()){

        } else {
            System.err.println("Apollon has not been initialized correctly!");
        }
    }

    private void reportExceptionAsIssue(Exception exception, DetailLevel detailLevel,
                                        CensoringLevel censoringLevel){

    }

    public DetailLevel getDetailLevel(){
        return detailLevel;
    }

    public void setDetailLevel(DetailLevel detailLevel){
        this.detailLevel = detailLevel;
    }

    public CensoringLevel getCensoringLevel(){
        return censoringLevel;
    }

    public void setCensoringLevel(CensoringLevel censoringLevel){
        this.censoringLevel = censoringLevel;
    }

    /**
     * If you have code that is volatile and might make Apollon to throw IOExceptions when connecting to github,
     * then use this function to determine if it was initialized correctly or not.
     * It's good practice to check it either way with this function - in case it cannot connect to github,
     * you can leave the {@link Thread.UncaughtExceptionHandler} empty or supply a different one.
     *
     * @return returns true if Apollon is ready to submit issues - in the case it is not, it will return false.
     */
    public boolean isApollonInitialized(){
        return reportingUnit != null;
    }
}
