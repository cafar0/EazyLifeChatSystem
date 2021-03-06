package chatServer;

import common.Message;
import common.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public interface ChatServerI extends Remote{


    String joinChat(String chat, User user) throws RemoteException;

    String createChatRoom(String chatRoom, User user) throws RemoteException;

    String createPrivateChat(String endUser, User user) throws RemoteException;

    String sendMessage(Message message, String chatRoomName, User user) throws RemoteException;

    String sendPrivateMessage(Message message, String endUser, User user) throws RemoteException;

    void getHistory(User user) throws RemoteException;

    List<String> getPublicChatList(User user) throws RemoteException;

    void logout(User user) throws RemoteException;
}
