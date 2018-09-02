package ch.rs.IssueReported.credentials;

public class Account {

    private String username, password;
    private boolean lock = false;

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){
        if(lock){
            return null;
        }
        return username;
    }

    public String getPassword(){
        if(lock){
            return null;
        }
        lock = true;
        return password;
    }

    public void dispose(){
        username = null;
        password = null;

    }

}
