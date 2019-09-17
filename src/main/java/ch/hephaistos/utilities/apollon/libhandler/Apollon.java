package ch.hephaistos.utilities.apollon.libhandler;


import ch.hephaistos.utilities.apollon.reporter.ReportingUnit;

public class Apollon implements Runnable {

    private String oAuthKey;
    private String repoName;
    private String branch;
    private ReportingUnit reporter;

    public Apollon() {
    }


    public Apollon(String oAuthKey, String repoName, String branch) {
        this.oAuthKey = oAuthKey;
        this.repoName = repoName;
        this.branch = branch;
        setupReportingUnit();

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
