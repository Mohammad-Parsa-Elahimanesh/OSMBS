package logic;

import logic.manager.Constants;
import logic.manager.Manager;

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
    public int sumScore = 0;
    public int onlineCoins = 0;
    public int gems = 0;
    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
        users.add(this);
        sumScore = (int)(Math.random()*10000); // FIXME Temporary for test standing
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
    public int getLevel() {return Math.max(0, 31 - Integer.numberOfLeadingZeros((int)(sumScore* Manager.constants.get(Constants.SHOP_LEVELMULTIPLIER))));}
}
