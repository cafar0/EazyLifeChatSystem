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
        users.add(user);
        this.name = name;
    }

    public String getName() {
        return  name;
    }

    public void addUser(String user) {
        users.add(user);
    }
}
