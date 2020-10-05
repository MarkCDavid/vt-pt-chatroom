package network.handlers;

import network.connection.BaseConnection;
import network.networkmessage.NetworkMessage;

public interface RequestHandlingSupport<C extends BaseConnection> {

    void handle(C connection, NetworkMessage message);
}
