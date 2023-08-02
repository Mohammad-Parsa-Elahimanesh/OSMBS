package logic;

import java.util.ArrayList;
import java.util.List;

public class User {
    private static final List<User> users = new ArrayList<>();
    public final List<Record> records = new ArrayList<>();
    public final String name;
    public final int password;
    public int offlineCoins = 0;
    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
        users.add(this);
    }

    public static List<User> getUsers() {
        return new ArrayList<>(users);
    }
}
