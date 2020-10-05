package network.handlers;

import network.connection.BaseConnection;
import network.networkmessage.NetworkMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkMessageProxy<C extends BaseConnection> {

    public boolean proxy(C connection, NetworkMessage message) {
        ensureHandlerContainerExists(message.getClass());
        for(RequestHandlingSupport<C> handler: handlers.get(message.getClass())) {
            handler.handle(connection, message);
            if(message.isHandled())
                return true;
        }
        return false;
    }


    public <T extends NetworkMessage> void subscribe(Class<T> type, RequestHandlingSupport<C> handler) {
        ensureHandlerContainerExists(type);
        handlers.get(type).add(handler);
    }

    public <T extends NetworkMessage> void unsubscribe(Class<T> type, RequestHandlingSupport<C> handler) {
        ensureHandlerContainerExists(type);
        handlers.get(type).remove(handler);
    }

    private <T extends NetworkMessage> void ensureHandlerContainerExists(Class<T> type) {
        if(!handlers.containsKey(type))
            handlers.put(type, new ArrayList<>());
    }


    public NetworkMessageProxy() {
        this.handlers = new HashMap<>();
    }

    private final Map<Class<? extends NetworkMessage>, List<RequestHandlingSupport<C>>> handlers;

}
