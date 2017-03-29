package common;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class DatabaseEntry {
    private String user, password;

    public DatabaseEntry(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
