package client;

import common.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Antoniu on 30-Mar-17.
 */
public class ClientService extends UnicastRemoteObject implements ClientServiceI {

    public ClientService() throws RemoteException{
        super();
    }

    @Override
    public void messageToClient(Message message) throws RemoteException {
        System.out.println(message);
    }
}
