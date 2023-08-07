package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PvChats {

    static final Map<String, List<SMS>> massages = new HashMap<>();

    private static String hash2Users(User a, User b) {
        return a.name.compareTo(b.name) < 0 ? a.name + " " + b.name : b.name + " " + a.name;
    }

    public static List<SMS> getMessages(User a, User b) {
        return massages.getOrDefault(hash2Users(a, b), new ArrayList<>());
    }

    public static void send(User sender, User receiver, String massage) {
        synchronized (massages) {
            massages.computeIfAbsent(hash2Users(sender, receiver), e -> new ArrayList<>());
            massages.get(hash2Users(sender, receiver)).add(new SMS(sender, massage));
        }
    }

    public static long lastMassageTime(User a, User b) {
        massages.computeIfAbsent(hash2Users(a, b), e -> new ArrayList<>());
        List<SMS> list = massages.get(hash2Users(a, b));
        return list.isEmpty() ? -1 : list.get(list.size() - 1).sendingTime;
    }
}
