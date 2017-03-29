package usersServer;

import database.AccessAccDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class UsersServer extends UnicastRemoteObject implements UsersServerI{
    AccessAccDB db = new AccessAccDB();

    public UsersServer() throws RemoteException{
        super();
    }

    public String signUp(String username, String password) throws RemoteException{

        if(db.addAccount(username, hashPassword(password))) {
            return "ok";
        }

        return null;
    }

    public String logIn(String username, String password) throws RemoteException {

        if (!db.authenticatePassword(username, password))
            return null;

        byte[] sessionID = new byte[64];
        new Random().nextBytes(sessionID);

        System.out.println("set session id: " + db.setSessionID(username, Arrays.toString(sessionID)));

        return Arrays.toString(sessionID);

    }


    private String hashPassword(String password) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            System.out.println("No such algorithm!!");
        }

        byte[] pwHash = {0};
        if (digest != null) {
            digest.update(password.getBytes());
            pwHash = digest.digest();
        }
        System.out.println(Arrays.toString(pwHash));
        return Arrays.toString(pwHash);
    }


}
