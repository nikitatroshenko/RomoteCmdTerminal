package by.bsu.cmdsocket.server.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Никита on 24.04.2016.
 */
public enum ConnectionsKeeper {

    INSTANCE;
    private final Map<String, ServerThread> connectionsMap;

    ConnectionsKeeper() {
        connectionsMap = new HashMap<>();
    }

    public void addServerThreadForUser(String userName, ServerThread serverThread) {
        connectionsMap.put(userName, serverThread);
    }

    public ServerThread getServerThreadByUserName(String userName) {
        return connectionsMap.get(userName);
    }

    public void removeUser(String userName) {
        connectionsMap.remove(userName);
    }

    public boolean userAlreadyConnected(String userName) {
        return connectionsMap.containsKey(userName);
    }

    public Set<String> getConnectedUsers() {
        return Collections.unmodifiableSet(connectionsMap.keySet());
    }
}
