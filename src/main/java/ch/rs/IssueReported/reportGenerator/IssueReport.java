package ch.rs.IssueReported.reportGenerator;

import ch.rs.IssueReported.tools.text.CensorLevel;
import ch.rs.IssueReported.tools.text.DetailLevel;

public class IssueReport {

    private final String NEW_ISSUE = "A new Exception has been thrown: ";
    private final String EXISTING_ISSUE = "This Exception has occurred again: ";
    private final String DETAIL_LEVEL = "The set detail level is: ";
    private final String CENSORING_MODE = "The set Censor Mode is: ";
    private final String CAUSE = "The cause for this Exception was: ";
    private final String TITLE_FORMATTING = "##";
    
    private final String NEW_ISSUE_TITLE ="Issue ";
    
    private final String ISSUE_HASH = " [IH]";
    
    private DetailLevel textDetail = DetailLevel.DETAILED;
    private CensorLevel censorMode = CensorLevel.NORMAL;

    private Exception savedException;
    
    private String title;

    /**
     * To compare Issues, the HashCode should be generated out of
     * the line number, the class it happened in and the thrown exception
     * This way, one can change Detial level aswell as Censoring,
     * and the issue will still report to the already existing issue on Github.
     */
    private int issueHash;
    
    private final String NEWLINE = System.lineSeparator();
    
    private StringBuilder textGenerator;

    public IssueReport(Exception e){
        savedException = e;
        generateIssueHash();
    }

    public void generateNewIssueText(){
        generateIssueStringBuilder();
        generateIssueTitle();
        generateBody();
    };
    
    public void generateCommentOnIssueText(){
        generateCommentStringBuilder();
        generateBody();
    };
    
    private void generateBody(){
        for(StackTraceElement ste : savedException.getStackTrace()){
            textGenerator.append(textDetail.getDetails(ste));
        }
    }
    
    private void generateIssueTitle(){
        title = new StringBuilder()
                .append(NEW_ISSUE_TITLE)
                .append(savedException.getClass())
                .append(ISSUE_HASH)
                .append(issueHash)
                .toString();
    }
    
    private void generateIssueStringBuilder(){
        textGenerator = new StringBuilder()
                            .append(NEW_ISSUE)
                            .append(savedException.getClass())
                            .append(NEWLINE)
                            .append(CAUSE)
                            .append(censorMode.getCausingObject(savedException))
                            .append(NEWLINE)
                            .append(DETAIL_LEVEL)
                            .append(textDetail.name())
                            .append(NEWLINE)
                            .append(CENSORING_MODE)
                            .append(censorMode.name())
                            .append(NEWLINE)
                            .append(TITLE_FORMATTING)
                            .append(NEWLINE);
    }
    
    private void generateCommentStringBuilder(){
        textGenerator = new StringBuilder()
                            .append(EXISTING_ISSUE)
                            .append(savedException.getClass())
                            .append(NEWLINE)
                            .append(CAUSE)
                            .append(censorMode.getCausingObject(savedException))
                            .append(NEWLINE)
                            .append(DETAIL_LEVEL)
                            .append(textDetail.name())
                            .append(NEWLINE)
                            .append(CENSORING_MODE)
                            .append(censorMode.name())
                            .append(NEWLINE)
                            .append(TITLE_FORMATTING)
                            .append(NEWLINE);
    }
    
    private void generateIssueHash(){
        issueHash = new StringBuilder()
                        .append(savedException.getClass())
                        .append(savedException.getStackTrace()[0].getLineNumber())
                        .append(savedException.getStackTrace()[0].getClassName())
                        .toString().hashCode();
    }

    public String getBody(){
        return textGenerator.toString();
    }
    
    public int getIssueHash(){
        return issueHash;
    }

    public String getTitle(){
        return title;
    }

    private String getCause(Exception e){
        return censorMode.getCausingObject(e);
    }

    public void setCensorMode(CensorLevel mode){
        censorMode = mode;
    }

    public void setDetailLevel(DetailLevel level){ 
        textDetail = level;
    }

}
