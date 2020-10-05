package network.handlers;

import network.message.Message;

public interface MessageHandlingSupport {
    void handle(Message message);
}
