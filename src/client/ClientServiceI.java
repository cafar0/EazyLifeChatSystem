package client;

import common.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Antoniu on 30-Mar-17.
 */
public interface ClientServiceI extends Remote {

    void messageToClient(Message message) throws RemoteException;


}
