package chatServer;

import common.ChatRoom;
import common.Message;
import common.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class chatServer implements chatServerI{
    List<ChatRoom> chatRooms = new ArrayList<>();

    public String joinChat(String chat, User user) {
        return "ok/nok";
    }

    public String createChatRoom(String chatRoom, User user) {
        return "ok/nok";
    }

    public String createPrivateChat(String endUser, User user) {
        return "ok/nok";
    }

    public void sendMessage(Message message, String chatRoomName, User user) {

    }

    public void sendPrivateMessage(Message message, String endUser, User user) {

    }

}
