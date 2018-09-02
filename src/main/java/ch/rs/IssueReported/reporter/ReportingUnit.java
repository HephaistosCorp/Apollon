package ch.rs.IssueReported.reporter;

import ch.rs.IssueReported.credentials.Account;
import ch.rs.IssueReported.reportGenerator.IssueReport;
import ch.rs.IssueReported.tools.text.CensorLevel;
import org.kohsuke.github.*;

import java.io.IOException;

/**
 * https://github.com/git/git/blob/master/README#L18-L20
 * ^use above link to highlight lines in code. This will allow the reporter
 * to create direct links to the line that caused the issue.
 *
 */

public class ReportingUnit {

    private String username;
    private String password;
    private CensorLevel censoringMode = CensorLevel.NORMAL;
    private GHBranch branch;
    GitHub github;
    GHRepository repo;

    public ReportingUnit(Account account) {
        this.username = account.getUsername();
        password = account.getPassword();
        try {
            github = GitHub.connectUsingPassword(username, password);
        } catch (IOException e){
            System.out.println("reportingUnitNew " + e.getMessage());
        }
    }

    public void setBranch(String branchName){
        try {
            this.branch = repo.getBranch(branchName);
            GHContent content = branch.getOwner().getFileContent("Initializer.java");
            System.out.println(content.getHtmlUrl());
        } catch (IOException e) {
            System.out.println("SetBranch threw an Exception " + e.getMessage());
        }
    }


    public void setRepository(String owner, String repositoryName){
        repo = getRepository(owner, repositoryName);
            try {
                branch = repo.getBranch(repo.getDefaultBranch());
            } catch (IOException e) {
                System.out.println("set Repo Error : " + e.getMessage());
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

    public void setCensoring(CensorLevel mode){
        censoringMode = mode;
    }

    private boolean checkErrorHash(){
        return true;
    }
}
