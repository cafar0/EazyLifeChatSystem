package chatServer;

import java.rmi.Naming;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class StartChatServer {
    public static void main(String args[]) {
        try {
            ChatServerI usi = new ChatServer();
            Naming.rebind("ChatService", usi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
