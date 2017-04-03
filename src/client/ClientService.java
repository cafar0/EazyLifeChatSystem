package client;

import common.Message;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Antoniu on 30-Mar-17.
 */
public class ClientService extends UnicastRemoteObject implements ClientServiceI {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public ClientService() throws RemoteException{
        super();
    }

    @Override
    public void messageToClient(Message message) throws RemoteException {
        if (message.getFrom().startsWith("private")) {
            System.out.println(ANSI_CYAN + message + ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN + message + ANSI_RESET);
        }
    }
}
