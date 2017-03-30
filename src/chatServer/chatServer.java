package chatServer;

import client.ClientServiceI;
import common.ChatRoom;
import common.DatabaseTemp;
import common.Message;
import common.User;
import database.AccessAccDB;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
            for (ChatRoom cr : chatRooms) {
                if (cr.getName().equals(chatRoom)) {
                    return null;
                }
            }
            chatRooms.add(new ChatRoom(chatRoom, user.getUsername()));
            return chatRoom;
        }
        return null;
    }

    public String createPrivateChat(String endUser, User user) {
        return "ok/nok";
    }


    public void sendMessage(Message message, String chatRoomName, User user) {
        System.out.println(-2);
        if (db.authenticateSession(user.getUsername(), user.getSessionId())) {
            System.out.println(-1);
            for (ChatRoom cr : chatRooms) {
                System.out.println(0);
                if (cr.getName().equals(chatRoomName)) {
                    System.out.println(1);
                    List<String> users = cr.getUsers();
                    for (String roomUser : users) {
                        System.out.println(2);
                        if (roomUser.equals(user.getUsername())){
                            System.out.println(3 + users.size());
                            for (String roomParticipant : users) {
                                System.out.println("am ajuns " + roomParticipant);
                                sendMessageToUser(new Message(chatRoomName + "@" + user.getUsername(), message.getMessage()), roomParticipant);
                            }
                            break;
                        }
                    }
                }
            }
        }

    }



    public void sendPrivateMessage(Message message, String endUser, User user) {

    }

    private void sendMessageToUser(Message message, String user) {
        try {
            System.out.println("rmi://127.0.0.1/" + user);
            ClientServiceI csi= (ClientServiceI) Naming.lookup("rmi://127.0.0.1/" + user);
            csi.messageToClient(message);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
