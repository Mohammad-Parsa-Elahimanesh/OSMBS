import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatCenter extends Thread {
    final List<Connection> newConnections = new ArrayList<>();
    final List<Connection> oldConnections = new ArrayList<>();
    private final Map<Connection, Chat> onlineConnections = new HashMap<>();

    @Override
    public void run() {
        while (true) {
            updateConnections();
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void updateConnections() {
        synchronized (newConnections) {
            for (Connection c : newConnections) {
                Chat newChat = new Chat(c, this);
                onlineConnections.put(c, newChat);
                newChat.start();
            }
            newConnections.clear();
        }
        synchronized (oldConnections) {
            for (Connection c : oldConnections)
                onlineConnections.remove(c);
            oldConnections.clear();
        }
    }

    Chat getChat(String name) {
        for (Map.Entry<Connection, Chat> E : onlineConnections.entrySet())
            if (E.getKey().user.name.equals(name))
                return E.getValue();
        for (Map.Entry<Connection, Chat> E : onlineConnections.entrySet())
            System.out.println(E.getKey().user.name);
        return null;
    }
}
