package logic.room;

import logic.GameMode;
import logic.SMS;
import logic.User;

import java.util.*;

public class Room {
    public static final List<Room> allRooms = new ArrayList<>();
    static final double DELAY =  1;
    public final User creator;
    public final Map<User, AccessLevel> gamers = new HashMap<>();
    public final Map<User, AccessLevel> watchers = new HashMap<>();
    public final List<User> kicked = new ArrayList<>();
    public final List<SMS> chats = new ArrayList<>();
    public RoomState state = RoomState.OPEN;
    double allReadyTime = 0;
    public final List<User> ready = new ArrayList<>();
    public final String password;
    public final GameMode mode;

    public Room(User creator, String password, GameMode mode) {
        this.creator = creator;
        this.password = (password.length() == 0?null:password);
        this.mode = mode;
        gamers.put(creator, AccessLevel.BOSS);
        allRooms.add(this);
    }
    public static Room getUserRoom(User user) {
        for (Room room : allRooms)
            if(room.state != RoomState.FINISHED && (room.gamers.containsKey(user) || room.watchers.containsKey(user)))
                return room;
        return null;
    }
    public void add(User user) {
        if(!watchers.containsKey(user))
            gamers.put(user, AccessLevel.USER);
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
        ready.clear();
        state = RoomState.OPEN;
    }
    public void close() {
        state = RoomState.CLOSE;
    }
    public void sayReady(User user) {
        if(!ready.contains(user))
            ready.add(user);
    }
    public void sayUnready(User user) {
        ready.remove(user);
    }
    public void leave(User user) {
        if(user == creator)
            state = RoomState.FINISHED;
        watchers.remove(user);
        gamers.remove(user);
    }
    // TODO 30 seconds after all body ready game must be started
}
