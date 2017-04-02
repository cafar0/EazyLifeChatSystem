package common;

/**
 * Created by Antoniu on 02-Apr-17.
 */
public class PrivateChatRoom  {
    private String user, endUser, name;
    public PrivateChatRoom(String endUser, String user) {
        this.user = user;
        this.endUser = endUser;
        this.name = user + endUser;
    }

    public String getUser() {
        return user;
    }

    public String getEndUser() {
        return endUser;
    }

    public String getName() {
        return name;
    }
}
