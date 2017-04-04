package client;

import chatServer.ChatServerI;
import common.Message;
import common.User;
import usersServer.UsersServerI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class Client {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private User user;
    private UsersServerI usersServerI;
    private StartClientService clientService;
    private ChatServerI chatServerI;
    private Scanner in;


    public Client() {
        try {
            in = new Scanner(System.in);
            clientService = new StartClientService();
            usersServerI = (UsersServerI) Naming.lookup("rmi://127.0.0.1/AuthService");
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
            return usersServerI.signUp(user, password) != null;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean login(String user, String password) {
        try {
            String sessionId;
            sessionId = usersServerI.logIn(user, password);
            if(sessionId == null) {
                System.out.println("Could not login. Wrong credentials");
                return false;
            }
            this.user = new User(user, sessionId);
            clientService.startClientService(user);
            chatServerI.getHistory(this.user);
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void logout() {
        try {
            chatServerI.logout(user);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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

    private void loginInterraction(Client client) {
        String input;
        boolean success = false;

        while (success == false) {
            System.out.println("Select choice number:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println();
            input = in.nextLine();
            String username, pass;
            switch (input) {
                case "1":
                    System.out.println("Register:");
                    System.out.print("Username: ");
                    username = in.nextLine();
                    System.out.print("Password: ");
                    pass = in.nextLine();
                    if (client.signup(username, pass)) {
                        System.out.println("Register successful: " + username);
                    } else {
                        System.out.println(ANSI_RED + "Username already existent or password not ok" + ANSI_RESET);
                        System.out.println(ANSI_RED + "Password must be between 6 and 24 characters without escaping ones" + ANSI_RESET);
                        break;
                    }
                case "2":
                    System.out.println("Login:");
                    System.out.print("Username: ");
                    username = in.nextLine();
                    System.out.print("Password: ");
                    pass = in.nextLine();
                    if (client.login(username, pass)) {
                        success = true;
                        System.out.println("Login successful " + username);
                        chatRoomInterraction(client);
                    } else {
                        System.out.println(ANSI_RED + "Login unsuccessful" + ANSI_RESET);
                    }
            }
        }
    }

    private void printMenu() {
        System.out.println("Write #menu for this menu");
        System.out.println("Select the choice number");
        System.out.println("1. Create chat room");
        System.out.println("2. Create private chat");
        System.out.println("3. Join chat room");
        System.out.println("4. Send chat room message");
        System.out.println("5. Send private message");
        System.out.println("6. Logout");
    }

    private void chatRoomInterraction(Client client) {
        String input;
        printMenu();
        while (true) {

            input = in.nextLine();
            if (input.equals("#menu")) {
                printMenu();
                continue;
            }
            String messageBody;
            String chatRoom;
            switch (input) {
                case "1":
                    System.out.println("Creating chat");
                    System.out.print("Write the name of the chat: ");
                    chatRoom = in.nextLine();
                    if (createChat(chatRoom))
                        System.out.println("Chat created successfully");
                    else
                        System.out.println(ANSI_RED + "Chat could not be created. Other One with the same name exists" + ANSI_RESET);
                    break;
                case "2":
                    String user;
                    System.out.println("Creating private chat");
                    System.out.print("Write the user name: ");
                    user = in.nextLine();
                    if (createPrivateChat(user))
                        System.out.println("Chat created successfully");
                    else
                        System.out.println(ANSI_RED + "User does not exist" + ANSI_RESET);
                    break;
                case "3":
                    System.out.println("Joining chat room");
                    System.out.print("Write chat room name: ");
                    chatRoom = in.nextLine();
                    if (joinChat(chatRoom)) {
                        System.out.println("Joined chat successfully");
                    } else {
                        System.out.println(ANSI_RED + "Chat not existent" + ANSI_RESET);
                    }
                    break;
                case "4":
                    System.out.println("Sending chat room message");
                    System.out.print("Write chat room to send to: ");
                    chatRoom = in.nextLine();
                    System.out.print("Write message: ");
                    messageBody = in.nextLine();
                    sendMessage(new Message(this.user.getUsername(), messageBody), chatRoom);
                    break;
                case "5":
                    String peerUser;
                    System.out.println("Sending private message");
                    System.out.print("Write user name: ");
                    peerUser = in.nextLine();
                    System.out.print("Write message: ");
                    messageBody = in.nextLine();
                    sendPrivateMessage(new Message(this.user.getUsername(), messageBody), peerUser);
                    break;
                case "6":
                    System.out.println("Logging out");
                    logout();
                    loginInterraction(client);
                    return;
                default:
                        System.out.println(ANSI_RED + "Wrong input" + input + ANSI_RESET);
                        printMenu();
            }

        }
    }



    public static void main(String args[]) {
        try {
            //stiu ca arata urat dar aici urmeaza sa lucram!
            /*Client client = new Client();
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
            client.sendPrivateMessage(new Message(name, message), name2);*/

            Client client = new Client();
            client.loginInterraction(client);

            //client.chatRoomInterraction(client);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
