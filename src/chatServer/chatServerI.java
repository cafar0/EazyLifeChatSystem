package chatServer;

import common.Message;
import common.User;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public interface chatServerI {


    String joinChat(String chat, User user);

    String createChatRoom(String chatRoom, User user);

    String createPrivateChat(String endUser, User user);

    void sendMessage(Message message, String chatRoomName, User user);

    void sendPrivateMessage(Message message, String endUser, User user);
}
