package network.request;

import logic.Record;
import logic.User;
import network.server.Connection;

public class Request {
    final Connection connection;
    public Request(Connection connection){this.connection = connection;}
    public void users() {
        StringBuilder result = new StringBuilder(User.getUsers().size()+"");
        for(User user: User.getUsers())
            result.append(" ").append(user.name).append(" ").append(user.password);
        connection.send(result);
    }
    public void signUp(String username, String password) {
        for(User user: User.getUsers())
            if(user.name.equals(username))
            {
                connection.send(SignUpStatus.NON_UNIQUE_USERNAME);
                return;
            }
        connection.send(SignUpStatus.SUCCESS);
        connection.user = new User(username, password);
    }
    public void signUp(String text) {
        text = text.trim();
        String[] tokens = text.split(" ");
        if(tokens.length != 2)
            connection.send(SignUpStatus.SPACE_IN_TEXT);
        else
            signUp(tokens[0], tokens[1]);
    }
    public void signIn(String username, String password) {
        for(User user: User.getUsers()) {
            if(user.name.equals(username)) {
                if(user.password == password.hashCode()) {
                    connection.send(SignInStatus.SUCCESS);
                    connection.user = user;
                } else
                    connection.send(SignInStatus.PASSWORD_INCORRECT);
                return;
            }
        }
        connection.send(SignInStatus.USER_NOT_EXIST);
    }
    public void signIn(String text) {
        text = text.trim();
        String[] tokens = text.split(" ");
        if(tokens.length != 2)
            connection.send(SignInStatus.SPACE_IN_TEXT);
        else
            signIn(tokens[0], tokens[1]);
    }
    public void updateOfflineCoins(int coins) {
        connection.user.offlineCoins += coins;
    }
    public void updateRecords(String text) {
        text = text.trim();
        String[] parts = text.split(" ");
        int[] intParts = new int[parts.length];
        for(int i = 0; i < parts.length; i++)
            intParts[i] = Integer.parseInt(parts[i]);
        int count = intParts[0];
        for(int i = 0; i < count; i++) {
            Record gameRecord = new Record();
            gameRecord.score = intParts[i*3+1];
            gameRecord.wholeTime = intParts[i*3+2];
            gameRecord.killedEnemies = intParts[i*3+3];
            if(!connection.user.records.contains(gameRecord))
                connection.user.records.add(gameRecord);
        }

    }
    public void coins() {
        connection.send(connection.user.offlineCoins);
        connection.user.offlineCoins = 0;
    }
    public void records() {
        StringBuilder res = new StringBuilder();
        res.append(connection.user.records.size());
        for(Record gameRecord : connection.user.records)
            res.append(' ').append(gameRecord.score).append(' ').append((int)gameRecord.wholeTime).append(' ').append(gameRecord.killedEnemies);
        connection.send(res.toString());
    }
}
