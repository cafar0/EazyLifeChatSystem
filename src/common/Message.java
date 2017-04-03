package common;

import java.io.Serializable;

/**
 * Created by Antoniu on 29-Mar-17.
 */
public class Message implements Serializable{
    String message, from;
    public Message(String from, String message) {
        this.from = from;
        this.message = message;
    }

    public String toString() {
        return from + ": " + message;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }
}

