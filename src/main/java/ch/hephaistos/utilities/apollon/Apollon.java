package ch.hephaistos.utilities.apollon;


import ch.hephaistos.utilities.apollon.reporting.ReportingUnit;
import ch.hephaistos.utilities.apollon.reporting.generation.text.CensoringLevel;
import ch.hephaistos.utilities.apollon.reporting.generation.text.DetailLevel;

import java.io.IOException;

public class Apollon implements Thread.UncaughtExceptionHandler {

    private static final String STANDARD_BRANCH = "master";
    private String OAuthKey;
    private String repoName;
    private String branch;
    private ReportingUnit reportingUnit;


    public Apollon(String OAuthKey, String repoOwner, String repoName) {
        this(OAuthKey, repoOwner, repoName, STANDARD_BRANCH);
    }


    public Apollon(String OAuthKey, String repoOwner, String repoName, String branch) {
        try {
            reportingUnit = new ReportingUnit(OAuthKey, repoOwner, repoName, branch);
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
    public synchronized void reportException(Exception exception) {
        if(reportingUnit != null){

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
    public synchronized void reportException(Exception exception, DetailLevel detailLevel,
                                             CensoringLevel censoringLevel) {
        if(reportingUnit != null){

        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(reportingUnit != null){

        }
    }
}
