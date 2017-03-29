package usersServer;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public interface UsersServerI {

    String signUp(String username, String password);

    String logIn(String username, String password);
}
