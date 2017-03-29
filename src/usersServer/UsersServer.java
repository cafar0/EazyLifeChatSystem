package usersServer;

import common.DatabaseEntry;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class UsersServer extends UnicastRemoteObject implements UsersServerI{
    private List<DatabaseEntry> db = new ArrayList<>();

    public UsersServer() throws RemoteException{
        super();
    }

    public String signUp(String username, String password) throws RemoteException{
        for (DatabaseEntry dbe : db) {
            if (dbe.getUser().equals(username)) {
                return null;
            }
        }
        db.add(new DatabaseEntry(username, hashPassword(password)));

        return "ok";
    }

    public String logIn(String username, String password) throws RemoteException{

        for (DatabaseEntry dbe : db) {
            if (dbe.getUser().equals(username))
                if (hashPassword(password).equals(dbe.getPassword())) {

                    byte[] sessionID = new byte[64];
                    new Random().nextBytes(sessionID);


                    return Arrays.toString(sessionID);
                }
        }
        return null;
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
