package usersServer;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class UsersServer implements UsersServerI{

    public String signUp(String username, String password) {
        return "ok/nok";
    }

    public String logIn(String username, String password) {
        return "ok/nok sesionid";
    }


}
