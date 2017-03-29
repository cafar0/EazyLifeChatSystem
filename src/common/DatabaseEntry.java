package common;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class DatabaseEntry {
    private String user, password, sessionId;

    public DatabaseEntry(String user, String password, String sessionId) {
        this.user = user;
        this.password = password;
        this.sessionId = sessionId;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
