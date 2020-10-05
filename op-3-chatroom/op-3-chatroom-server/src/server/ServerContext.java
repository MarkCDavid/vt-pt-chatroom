package server;

import network.handlers.MessageProxy;
import network.networkmessage.*;
import server.handlers.ClientChatMessageHandler;
import server.handlers.LoginRequestHandler;
import server.handlers.LogoutRequestHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerContext {

    public ServerContext() {
        this.connections = Collections.synchronizedList(new ArrayList<>());
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public void addConnection(Connection connection) {
        UserLoggedInNetworkMessage userLoggedInNM = new UserLoggedInNetworkMessage(connection.getUsername());
        connection.write(userLoggedInNM);
        for(Connection c: connections) {
            c.write(userLoggedInNM);
            connection.write(new UserLoggedInNetworkMessage(c.getUsername()));
        }
        connections.add(connection);
    }

    public void removeConnection(Connection connection) {
        UserLoggedOutNetworkMessage userLoggedOutNM = new UserLoggedOutNetworkMessage(connection.getUsername());
        if(!connections.remove(connection))
            return;

        for(Connection c: connections) {
            c.write(userLoggedOutNM);
        }
    }

    public Runnable getProxy(ServerContext context, Connection connection) {
        return () -> {
            MessageProxy<Connection> proxy = new MessageProxy<>();
            proxy.subscribe(LoginRequestNetworkMessage.class, new LoginRequestHandler(context));
            proxy.subscribe(ClientChatMessageNetworkMessage.class, new ClientChatMessageHandler(context));
            proxy.subscribe(LogoutRequestNetworkMessage.class, new LogoutRequestHandler(context));

            while (true) {
                if(!connection.isValid()){
                    closeConnection(connection);
                    break;
                }

                NetworkMessage message = connection.read();

                if(!proxy.proxy(connection, message)) {
                    closeConnection(connection);
                    break;
                }
            }
        };
    }

    private void closeConnection(Connection connection) {
        connection.close();
        this.removeConnection(connection);
        System.out.printf("Connection with %s lost.%n", connection.getAddress());
    }

    private final List<Connection> connections;
}
