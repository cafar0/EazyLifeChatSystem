package chatServer;

import common.*;
import database.AccessAccDB;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class ChatServer extends UnicastRemoteObject implements ChatServerI{
    List<ChatRoom> chatRooms = new ArrayList<>();
    private AccessAccDB db = new AccessAccDB();

    public ChatServer() throws RemoteException{
        super();
    }

    public String joinChat(String chat, User user) {
        return "ok/nok";
    }

    public String createChatRoom(String chatRoom, User user) {
        System.out.println("Starting");
        System.out.println(chatRoom);
        System.out.println(user.getUsername());
        DatabaseTemp.stupid();
        if (db.authenticateSession(user.getUsername(), user.getSessionId())) {
            chatRooms.add(new ChatRoom(chatRoom, user.getUsername()));
            return chatRoom;
        }

        return null;
    }

    public String createPrivateChat(String endUser, User user) {
        return "ok/nok";
    }

    public void sendMessage(Message message, String chatRoomName, User user) {

    }

    public void sendPrivateMessage(Message message, String endUser, User user) {

    }

}
