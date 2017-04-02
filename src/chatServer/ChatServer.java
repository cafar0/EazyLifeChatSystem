package chatServer;

import client.ClientServiceI;
import common.*;
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
    List<PrivateChatRoom> privateChatRooms = new ArrayList<>();
    private AccessAccDB db = new AccessAccDB();

    public ChatServer() throws RemoteException{
        super();
    }

    public String joinChat(String chatRoom, User user) {
        if (db.authenticateSession(user.getUsername(), user.getSessionId())) {
            ChatRoom searchedChatRoom = null;
            for (ChatRoom cr : chatRooms) {
                if (cr.getName().equals(chatRoom)) {
                    searchedChatRoom = cr;
                    break;
                }
            }

            if (searchedChatRoom == null) {
                return null;
            }

            for (String chatUser : searchedChatRoom.getUsers()) {
                if (chatUser.equals(user.getUsername())) {
                    return null;
                }
            }
            System.out.println("User " + user.getUsername() + " added to the chatroom");
            searchedChatRoom.addUser(user.getUsername());
            return "ok";
        }

        return null;
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
        if (!db.authenticateSession(user.getUsername(), user.getSessionId())) {
            System.out.println("authentication failed");
            return null;
        }

        if (!db.accExists(endUser)) {
            System.out.println("acc already existent");
            return null;
        }

        String possibleChatName1 = user.getUsername() + endUser, posssibleChatName2 = endUser + user.getUsername();
        //name the chat after the names of the user for easing the search between chats
        for (PrivateChatRoom pcr : privateChatRooms) {
            if (pcr.getName().equals(possibleChatName1)
                    || (pcr.getName().equals(posssibleChatName2))
                    || user.getUsername().equals(endUser)) {
                return null;
            }
        }
        privateChatRooms.add(new PrivateChatRoom(user.getUsername(), endUser));
        return "ok";
    }


    public String sendMessage(Message message, String chatRoomName, User user) {
        if (db.authenticateSession(user.getUsername(), user.getSessionId())) {
            for (ChatRoom cr : chatRooms) {
                if (cr.getName().equals(chatRoomName)) {
                    List<String> users = cr.getUsers();
                    for (String roomUser : users) {
                        if (roomUser.equals(user.getUsername())){
                            for (String roomParticipant : users) {
                                System.out.println("am ajuns " + roomParticipant);
                                sendMessageToUser(new Message(chatRoomName + "@" + user.getUsername(), message.getMessage()), roomParticipant);
                            }
                            return "ok";
                        }
                    }
                }
            }
        }
        return null;
    }


    //this will return if the message was sent
    public String sendPrivateMessage(Message message, String endUser, User user) {
        if (db.authenticateSession(user.getUsername(), user.getSessionId())) {
            for (PrivateChatRoom pcr : privateChatRooms) {
                String possibleChatName1 = user.getUsername() + endUser, posssibleChatName2 = endUser + user.getUsername();
                if (pcr.getName().equals(possibleChatName1) || pcr.getName().equals(posssibleChatName2)) {
                    System.out.println("sending message to user and enduser " + user.getUsername() + " " + endUser);
                    sendMessageToUser(new Message("private@" + user.getUsername(), message.getMessage()), endUser);
                    sendMessageToUser(new Message("private@" + user.getUsername(), message.getMessage()), user.getUsername());
                    return "ok";
                }
            }
        }
        return null;
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
