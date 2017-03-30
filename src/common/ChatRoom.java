package common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class ChatRoom implements Serializable{
    private List<String> users = new ArrayList<>();
    private String name;

    public ChatRoom(String name, String user) {
        this.name = name;
        addUser(user);
    }

    public String getName() {
        return  name;
    }

    public void addUser(String user) {
        users.add(user);
    }

    public List<String> getUsers() {
        return users;
    }

    public void removeUser(String name) {
        for (String user : users) {
            if(user.equals(name)) {
                users.remove(user);
                return;
            }
        }
    }
}
