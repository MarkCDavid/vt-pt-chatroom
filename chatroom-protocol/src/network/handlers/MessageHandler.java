package network.handlers;

import network.message.Message;

public abstract class MessageHandler<M extends Message> implements MessageHandlingSupport {

    public void handle(Message message) {
        //noinspection unchecked
        this.handleCore((M) message);
    }

    protected abstract void handleCore(M message);
}