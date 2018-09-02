package ch.rs.IssueReported.libhandler;


import ch.rs.IssueReported.credentials.Account;
import ch.rs.IssueReported.reportGenerator.IssueReport;
import ch.rs.IssueReported.reporter.ReportingUnit;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Initializer implements Runnable {

    private Account account;
    private String owner = "FancyJavaStuff";
    private String repository = "IssueReported";


    @Override
    public void run() {

        account = new Account("", "");
        ReportingUnit rUnit = new ReportingUnit(account);
        rUnit.setRepository(owner, repository);

        MavenXpp3Reader reader = new MavenXpp3Reader();
        try {
            Model model = reader.read(new FileReader("pom.xml"));
            System.out.println(model.getVersion());
        } catch (Exception e) {

            System.out.println("FileNotFoundFor Model : " + e.getMessage());
        }

        rUnit.setBranch("IssueTesting");
        rUnit.reportIssueToRepository(new NullPointerException("null"));
        rUnit.reportIssueToRepository(new IOException("No rights to do this"));
        rUnit.reportIssueToRepository(new IndexOutOfBoundsException("4"));

        }

}
