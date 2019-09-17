package ch.hephaistos.utilities.apollon;


import ch.hephaistos.utilities.apollon.reporting.ReportingUnit;

public class Apollon implements Runnable {

    private static final String STANDARD_BRANCH = "master";
    private String OAuthKey;
    private String repoName;
    private String branch;
    private ReportingUnit reporter;


    
    public Apollon(String OAuthKey, String repoName) {
        this(OAuthKey, repoName, STANDARD_BRANCH);
    }



    public Apollon(String OAuthKey, String repoName, String branch) {
        this.OAuthKey = OAuthKey;
        this.repoName = repoName;
        this.branch = branch;
    }

    @Override
    public void run() {


        /**
         rUnit.setBranch("IssueTesting");
         rUnit.reportIssueToRepository(new NullPointerException("null"));
         rUnit.reportIssueToRepository(new IOException("No rights to do this"));
         rUnit.reportIssueToRepository(new IndexOutOfBoundsException("4"));
         **/

    }

    private void setupReportingUnit() {
        reporter = new ReportingUnit("key");
    }


}
