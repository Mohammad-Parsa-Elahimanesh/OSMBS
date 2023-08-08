package logic;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final List<User> users = new ArrayList<>();
    public final List<User> friends = new ArrayList<>();
    public final List<Record> records = new ArrayList<>();
    public final String name;
    public final int password;
    public int offlineCoins = 0;
    public User invitedFrom;
    public final List<User> invited = new ArrayList<>();

    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
        users.add(this);
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }

    public static User find(String name) {
        for (User user : users)
            if (user.name.equals(name))
                return user;
        return null;
    }
}
