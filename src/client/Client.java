package client;

import chatServer.ChatServerI;
import common.Message;
import common.User;
import usersServer.UsersServerI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class Client {
    private User user;
    private UsersServerI usi ;
    private StartClientService clientService;
    private ChatServerI chatServerI;

    public Client() {
        try {
            clientService = new StartClientService();
            usi = (UsersServerI) Naming.lookup("rmi://127.0.0.1/AuthService");
            chatServerI = (ChatServerI) Naming.lookup("rmi://127.0.0.1/ChatService");
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    private boolean signup(String user, String password) {
        try {
            return usi.signUp(user, password) != null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean login(String user, String password) {
        try {
            String sessionId;
            sessionId = usi.logIn(user, password);
            if(sessionId == null) {
                System.out.println("Could not login. Wrong creditentials");
                return false;
            }
            this.user = new User(user, sessionId);
            clientService.startClientService(user);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean createChat(String chatRoom){
        try {
            System.out.println("chat room " + chatServerI.createChatRoom(chatRoom, user));
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean joinChat(String chatRoom) {
        return false;
    }

    private void sendMessage(Message message, String chatRoom) {
        try {
            chatServerI.sendMessage(message, chatRoom, user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        try {
            Client client = new Client();
            String name = "Username", pass = "password", chatRoom = "Discutam", message = "de ce dureaza atata??";
            client.signup(name, pass);

            client.login(name, pass);


            client.createChat(chatRoom);

            client.sendMessage(new Message(name, message), chatRoom);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
