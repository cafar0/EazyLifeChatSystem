package common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoniu on 02-Apr-17.
 */
public class PrivateChatRoom  {
    private String user, endUser, name;
    private List<Message> history = new ArrayList<>();
    public PrivateChatRoom(String endUser, String user) {
        this.user = user;
        this.endUser = endUser;
        this.name = user + endUser;
    }

    public String getUser() {
        return user;
    }

    public String getEndUser() {
        return endUser;
    }

    public String getName() {
        return name;
    }

    public void addMessageToHistory(Message message) {
        history.add(message);
    }

    public synchronized List<Message> getHistoryForUser(String user) {
        List <Message> userHistory = new ArrayList<>();
        for (Message m : history) {
            if (!m.getFrom().equals(user)) {
                userHistory.add(m);
            }
        }
        history = new ArrayList<>();
        return userHistory;
    }
}
