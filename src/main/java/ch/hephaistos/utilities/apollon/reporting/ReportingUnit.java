package ch.hephaistos.utilities.apollon.reporting;

import ch.hephaistos.utilities.apollon.reporting.generation.IssueReport;
import ch.hephaistos.utilities.apollon.reporting.generation.text.CensoringLevel;
import ch.hephaistos.utilities.apollon.reporting.generation.text.DetailLevel;
import org.kohsuke.github.*;

import java.io.IOException;

/**
 * https://github.com/git/git/blob/master/README#L18-L20
 * ^use above link to highlight lines in code. This will allow the reporting
 * to create direct links to the line that caused the issue.
 */

public class ReportingUnit {

    private GHBranch branch;
    private GitHub reportingKey;
    private GHRepository repository;
    private int commentLimit;
    private String username;

    public ReportingUnit(String OAuthKey, String repoOwner, String repoName, String branchName) throws IOException {
        this(OAuthKey, repoOwner, repoName, branchName, 0, "");
    }

    public ReportingUnit(String OAuthKey, String repoOwner, String repoName, String branchName,
                         int commentLimit, String username) throws IOException {
        reportingKey = GitHub.connectUsingOAuth(OAuthKey);
        repository = reportingKey.getRepository(repoOwner + "/" + repoName);
        branch = repository.getBranch(branchName);
        this.commentLimit = commentLimit;
        this.username = username;
    }

    private GHIssue getCorrespondingIssue(int hashCode) {
        try {
            for (GHIssue issue : repository.getIssues(GHIssueState.OPEN)) {
                //IF YOU USE BRACES READ UP ON FORMATTING FIRST
                if (issue.getTitle().split("IH]")[1].equals(String.valueOf(hashCode))) {
                    return issue;
                }
            }
            return new GHIssue();
        } catch (IOException e) {
            System.err.println("There was an error whilst searching for existing issues. " +
                    "Creating a new one: " + e.getMessage());
            return new GHIssue();
        }
    }

    public void createIssueFromException(Exception exception, DetailLevel detailLevel,
                                         CensoringLevel censoringLevel) {
        IssueReport issueReport = new IssueReport(exception);
        GHIssue issue = getCorrespondingIssue(issueReport.getIssueHash());
        if (issue.getTitle() != null) {
            try {
                if (!hasReachedCommentLimit(issue)) {
                    issueReport.generateCommentOnIssueText(censoringLevel, detailLevel);
                    issue.comment(issueReport.getBody());
                }
            } catch (IOException e) {
                System.err.println("Couldn't comment on existing issue " + e.getMessage());
            }
        } else {
            try {
                issueReport.generateNewIssueText(censoringLevel, detailLevel);
                GHIssueBuilder issueBuilder = repository.createIssue(issueReport.getTitle());
                issueBuilder.body(issueReport.getBody());
                issueBuilder.create();
            } catch (IOException e) {
                System.err.println("Couldn't create a new issue in the repo: " + e.getMessage());
            }
        }
    }

    private boolean hasReachedCommentLimit(GHIssue issue) {
        try {
            if (issue.getComments().size() < commentLimit || commentLimit == 0) {
                return false;
            } else {
                int counter = 0;
                for(GHIssueComment comment :issue.getComments()){
                    if(comment.getUser().getLogin().equals(username)) counter++;
                }
                return counter >= commentLimit;
            }
        } catch (IOException ioe) {
            System.err.println("Could't access the comments of the Issue. Aborting reporting: " + ioe.getMessage());
            return false;
        }
    }

}
