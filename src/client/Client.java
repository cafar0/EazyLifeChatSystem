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
            String chatCreated = chatServerI.createChatRoom(chatRoom, user);
            System.out.println("chat room " + chatCreated);
            if (chatCreated != null)
                return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean createPrivateChat(String endUser) {
        try {
            String chatCreated = chatServerI.createPrivateChat(endUser, user);
            System.out.println("private chat: " + chatCreated);
            if (chatCreated != null) {
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean joinChat(String chatRoom) {
        try {
            String joinedChatRoom = chatServerI.joinChat(chatRoom, user);
            System.out.println("Joining chat room result: " + joinedChatRoom);
            if (joinedChatRoom != null) {
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void sendMessage(Message message, String chatRoom) {
        try {
            if (chatServerI.sendMessage(message, chatRoom, user) == null)
                System.out.println("The message wasn't send");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendPrivateMessage(Message message, String endUser) {
        try {
            if (chatServerI.sendPrivateMessage(message, endUser, user) == null)
                System.out.println("The message wasn't send");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public static void main(String args[]) {
        try {
            //stiu ca arata urat dar aici urmeaza sa lucram!
            Client client = new Client();
            Client client2 = new Client();
            String name = "MyUsername", pass = "password", chatRoom = "Discut", message = "de ce dureaza atata??";
            String name2 = "Username2", pass2 = "password", chatRoom2 = "Discut", message2 = "Asta este 2";
            System.out.println("SignUp result: " + client.signup(name, pass));

            System.out.println("Login result: " + client.login(name, pass));


            System.out.println("Create chat room result: " + client.createChat(chatRoom));

            client.sendMessage(new Message(name, message), chatRoom);

            System.out.println("SignUp2 result: " + client2.signup(name2, pass));

            client.sendPrivateMessage(new Message(name, message), name2);

            System.out.println("Login2 result: " + client2.login(name2, pass));

            System.out.println("Joined chat2 result: " + client2.joinChat(chatRoom2));

            client.sendMessage(new Message(name, message), chatRoom);
            client2.sendMessage(new Message(name2, message2), chatRoom2);


            System.out.println("Sending private messages");
            client.createPrivateChat(name2);
            client.sendPrivateMessage(new Message(name, message), name2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
