package client;

import usersServer.UsersServerI;

import java.rmi.Naming;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class Client {
    public static void main(String args[]) {
        try {
            UsersServerI usi = (UsersServerI) Naming.lookup("rmi://127.0.0.1/AuthService");
            System.out.println(usi.signUp("andrei", "diana"));
            System.out.println(usi.signUp("andrei", "diana"));
            System.out.println(usi.logIn("ion","mda"));
            System.out.println("session id is: " + usi.logIn("andrei", "diana"));
        } catch (Exception e) {

        }
    }
}
