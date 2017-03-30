package chatServer;

import common.Message;
import common.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public interface ChatServerI extends Remote{


    String joinChat(String chat, User user) throws RemoteException;

    String createChatRoom(String chatRoom, User user) throws RemoteException;

    String createPrivateChat(String endUser, User user) throws RemoteException;

    void sendMessage(Message message, String chatRoomName, User user) throws RemoteException;

    void sendPrivateMessage(Message message, String endUser, User user) throws RemoteException;
}
