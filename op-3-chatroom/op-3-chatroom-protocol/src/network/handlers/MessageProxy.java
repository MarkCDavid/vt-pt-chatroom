package network.handlers;

import network.message.Message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageProxy {

    private final Map<Class<? extends Message>, List<MessageHandlingSupport>> handlers;

    public MessageProxy() {
        this.handlers = new HashMap<>();
    }

    public void proxy(Message message) {
        ensureHandlerContainerExists(message.getClass());
        for (MessageHandlingSupport handler : handlers.get(message.getClass())) {
            handler.handle(message);
        }
    }

    public void subscribe(Class<? extends Message> type, MessageHandlingSupport handler) {
        ensureHandlerContainerExists(type);
        handlers.get(type).add(handler);
    }

    public void unsubscribe(Class<? extends Message> type, MessageHandlingSupport handler) {
        ensureHandlerContainerExists(type);
        handlers.get(type).remove(handler);
    }

    private void ensureHandlerContainerExists(Class<? extends Message> type) {
        if (!handlers.containsKey(type))
            handlers.put(type, new ArrayList<>());
    }

}
