package common;

import java.io.Serializable;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class User implements Serializable{
    private String username;
    private String sessionId;

    public User(String username, String sessionId) {
        this.username = username;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }
}
