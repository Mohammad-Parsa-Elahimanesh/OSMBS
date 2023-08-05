package logic.room;

import logic.GameMode;
import logic.SMS;
import logic.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    static final List<Room> allRunningRooms = new ArrayList<>();
    static final double DELAY =  1;
    final User creator;
    public final Map<User, AccessLevel> gamers = new HashMap<>();
    public final Map<User, AccessLevel> watchers = new HashMap<>();
    public List<User> kicked = new ArrayList<>();
    public List<SMS> chats = new ArrayList<>();
    public RoomState state = RoomState.OPEN;
    final String password;
    final GameMode mode;

    public Room(User creator, String password, GameMode mode) {
        this.creator = creator;
        this.password = (password.length() == 0?null:password);
        this.mode = mode;
        gamers.put(creator, AccessLevel.BOSS);
        allRunningRooms.add(this);
    }
    public static Room getUserRoom(User user) {
        for (Room room : allRunningRooms)
            if(room.gamers.containsKey(user) || room.watchers.containsKey(user))
                return room;
        return null;
    }

    public AccessLevel getAccessLevel(User user) {
        return gamers.getOrDefault(user, watchers.getOrDefault(user, null));
    }
    public void setManager(User user) {
        gamers.computeIfPresent(user, (u, l) -> l == AccessLevel.BOSS?AccessLevel.BOSS:AccessLevel.MANAGER);
        watchers.computeIfPresent(user, (u, l) -> l == AccessLevel.BOSS?AccessLevel.BOSS:AccessLevel.MANAGER);
    }
    public void kick(User user) {
        synchronized (this) {
            kicked.add(user);
            gamers.remove(user);
            watchers.remove(user);
        }
    }
    public void changeRoleToGamer(User user) {
        if(watchers.containsKey(user)) {
            gamers.put(user, watchers.get(user));
            watchers.remove(user);
        }
    }
    public void changeRoleToWatcher(User user) {
        if(gamers.containsKey(user)) {
            watchers.put(user, gamers.get(user));
            gamers.remove(user);
        }
    }
    public void open() {
        // TODO
    }
    public void close() {
        // TODO
    }
    // TODO remove Room after ends from list

}
