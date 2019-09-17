package ch.hephaistos.utilities.apollon;


import ch.hephaistos.utilities.apollon.reporting.ReportingUnit;
import ch.hephaistos.utilities.apollon.reporting.generation.text.CensoringLevel;
import ch.hephaistos.utilities.apollon.reporting.generation.text.DetailLevel;

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
        reportingUnit = new ReportingUnit();
    }

    private void setupReportingUnit() {
        reporter = new ReportingUnit("key");
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
    public synchronized void reportException(Exception exception){

    }

    /**
     * This function allows you to dynamically supply exceptions to report to the repository.
     * The exception will be reported according to the given {@link DetailLevel DetailLevel}
     * and {@link CensoringLevel}. If you want to use the set options, use {@link #reportException(Exception)}
     *
     * @param exception the thrown exception to be reported
     * @param detailLevel the {@link DetailLevel} of the report
     * @param censoringLevel the {@link CensoringLevel} of the report
     */
    public synchronized void reportException(Exception exception, DetailLevel detailLevel,
                                                  CensoringLevel censoringLevel){

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

    }
}
