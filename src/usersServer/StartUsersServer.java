package usersServer;

import java.rmi.Naming;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class StartUsersServer {


    public static void main(String args[]) {
        /*UsersServer us = new UsersServer();
        System.out.println(us.signUp("andrei", "diana"));
        System.out.println(us.signUp("andrei", "diana"));
        System.out.println(us.logIn("ion","mda"));
        System.out.println("session id is: " + us.logIn("andrei", "diana"));*/
        try {
            UsersServerI usi = new UsersServer();
            Naming.rebind("AuthService", usi);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
