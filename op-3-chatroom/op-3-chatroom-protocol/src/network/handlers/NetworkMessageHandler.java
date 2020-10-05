package network.handlers;

import network.connection.BaseConnection;
import network.networkmessage.NetworkMessage;

public abstract class NetworkMessageHandler<C extends BaseConnection, M extends NetworkMessage> implements RequestHandlingSupport<C> {

    public void handle(C connection, NetworkMessage message) {
        if (message.isHandled())
            return;

        //noinspection unchecked
        this.handleCore(connection, (M) message);
    }

    protected abstract void handleCore(C connection, M message);
}
