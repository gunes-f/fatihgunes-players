package com.gunessoftware;

/**
 * Message implementation that represents the messages from one player to another.
 * Contains a String message and the player that sends the message.
 */
public class Message {
    private String message;
    private Player sender;

    public Message(String message, Player sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }
}
