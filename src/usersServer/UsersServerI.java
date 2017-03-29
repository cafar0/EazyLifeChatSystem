package usersServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public interface UsersServerI extends Remote {

    String signUp(String username, String password)throws RemoteException;

    String logIn(String username, String password) throws RemoteException;
}
