package logic.manager;

import logic.User;

import java.util.HashMap;
import java.util.Map;

public class BlockManager {
    static final Map<String, Boolean> iSBlock = new HashMap<>();

    private static String hash2Users(User a, User b) {
        return a.name + " " + b.name;
    }

    public static void block(User blocker, User blocked) {
        iSBlock.put(hash2Users(blocker, blocked), true);
    }

    public static void unblock(User blocker, User blocked) {
        iSBlock.put(hash2Users(blocker, blocked), false);
    }

    public static boolean isBlocked(User blocker, User blocked) {
        return iSBlock.getOrDefault(hash2Users(blocker, blocked), false);
    }
}
