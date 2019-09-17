package ch.hephaistos.utilities.apollon.reporting;

import ch.hephaistos.utilities.apollon.reporting.generation.IssueReport;
import ch.hephaistos.utilities.apollon.reporting.generation.text.CensoringLevel;
import org.kohsuke.github.*;

import java.io.IOException;

/**
 * https://github.com/git/git/blob/master/README#L18-L20
 * ^use above link to highlight lines in code. This will allow the reporting
 * to create direct links to the line that caused the issue.
 *
 */

public class ReportingUnit {

    private CensoringLevel censoringMode = CensoringLevel.NORMAL;
    private GHBranch branch;
    private GitHub github;
    private GHRepository repo;

    public ReportingUnit(String oAuthKey) {
        try {
            GitHub.connectUsingOAuth(oAuthKey);
        } catch (IOException e){
            System.out.println("reportingUnitNew " + e.getMessage());
        }
    }

    private GHRepository getRepository(String owner, String repository){
        try {
            return github.getRepository(owner + "/" + repository);
        } catch (IOException e) {
            System.out.println("getRepository " + e.getMessage());
            return null;
        }
    }

    private GHIssue issueExist(int hashCode){
        try {
            for (GHIssue issue : repo.getIssues(GHIssueState.OPEN)) {
                //IF YOU USE BRACES READ UP ON FORMATTING FIRST
                if(issue.getTitle().split("IH]")[1].equals(String.valueOf(hashCode))){
                    return  issue;
                }
            }
            return new GHIssue();
        }catch (IOException e){
            System.out.println("issueExist: " + e.getMessage());
            return new GHIssue();
        }
    }


    public void reportIssueToRepository(Exception exception){
        IssueReport issueReport = new IssueReport(exception);
        GHIssue issue = issueExist(issueReport.getIssueHash());
        if(issue.getTitle() != null){
            try {
                issueReport.generateCommentOnIssueText();
                issue.comment(issueReport.getBody());
            } catch (IOException e){
                System.out.println("reportIssueToRepository " + e.getMessage());
            }
            return;
        }
        try {
            issueReport.generateNewIssueText();
            GHIssueBuilder issueBuilder = repo.createIssue(issueReport.getTitle());
            issueBuilder.body(issueReport.getBody());
            issueBuilder.create();
        } catch (IOException e) {
            System.out.println("reportIssueToRepository " + e.getMessage());
        }
    }

    private String getCause(Exception e){
        return censoringMode.getCausingObject(e);
    }


    private GHIssue getIssue(int issueId) {
        try {
            return repo.getIssue(issueId);
        } catch (IOException e) {
            System.out.println("getIssue " + e.getMessage());
            return new GHIssue();
        }
    }

    public void setCensoring(CensoringLevel mode){
        censoringMode = mode;
    }

    private boolean checkErrorHash(){
        return true;
    }
}
