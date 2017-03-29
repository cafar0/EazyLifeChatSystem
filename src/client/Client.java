package client;

import chatServer.ChatServerI;
import common.DatabaseTemp;
import common.User;
import usersServer.UsersServerI;

import java.rmi.Naming;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class Client {
    public static void main(String args[]) {
        try {
            UsersServerI usi = (UsersServerI) Naming.lookup("rmi://127.0.0.1/AuthService");
            System.out.println(usi.signUp("andrei2", "diana"));
            System.out.println(usi.signUp("andrei", "diana"));
            System.out.println(usi.logIn("ion","mda"));
            String sessionId = usi.logIn("andrei2", "diana");
            System.out.println("session id: " + sessionId);
            User user = new User("andrei2", sessionId);

            DatabaseTemp.stupid();
            ChatServerI chatServerI = (ChatServerI) Naming.lookup("rmi://127.0.0.1/ChatService");
            System.out.println("chat room " + chatServerI.createChatRoom("FirstChat", user));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
