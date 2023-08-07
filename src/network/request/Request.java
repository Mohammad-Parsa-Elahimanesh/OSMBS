package network.request;

import logic.Record;
import logic.*;
import logic.room.AccessLevel;
import logic.room.Room;
import logic.room.RoomState;
import network.server.Connection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Request {
    final Connection connection;

    public Request(Connection connection) {
        this.connection = connection;
    }

    public void users() {
        StringBuilder result = new StringBuilder(User.getUsers().size() + "");
        for (User user : User.getUsers())
            result.append(" ").append(user.name).append(" ").append(user.password);
        connection.send(result);
    }

    public void friends() {
        connection.user.friends.sort((o1, o2) -> Long.compare(PvChats.lastMassageTime(o1, connection.user), PvChats.lastMassageTime(o2, connection.user)));
        connection.send(listUsersNamesToString(connection.user.friends));
    }

    private void signUp(String username, String password) {
        for (User user : User.getUsers())
            if (user.name.equals(username)) {
                connection.send(SignUpStatus.NON_UNIQUE_USERNAME);
                return;
            }
        connection.send(SignUpStatus.SUCCESS);
        connection.user = new User(username, password);
    }

    public void signUp(String text) {
        text = text.trim();
        String[] tokens = text.split(" ");
        if (tokens.length != 2)
            connection.send(SignUpStatus.SPACE_IN_TEXT);
        else
            signUp(tokens[0], tokens[1]);
    }

    private void signIn(String username, String password) {
        for (User user : User.getUsers()) {
            if (user.name.equals(username)) {
                if (user.password == password.hashCode()) {
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
        if (tokens.length != 2)
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
        for (int i = 0; i < parts.length; i++)
            intParts[i] = Integer.parseInt(parts[i]);
        int count = intParts[0];
        for (int i = 0; i < count; i++) {
            Record gameRecord = new Record();
            gameRecord.score = intParts[i * 3 + 1];
            gameRecord.wholeTime = intParts[i * 3 + 2];
            gameRecord.killedEnemies = intParts[i * 3 + 3];
            if (!connection.user.records.contains(gameRecord))
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
        for (Record gameRecord : connection.user.records)
            res.append(' ').append(gameRecord.score).append(' ').append((int) gameRecord.wholeTime).append(' ').append(gameRecord.killedEnemies);
        connection.send(res.toString());
    }

    public void rooms(String modeName) {
        GameMode mode = GameMode.valueOf(modeName);
        List<Room> rooms = new ArrayList<>();
        for (Room room : Room.allRooms)
            if (room.state == RoomState.OPEN && room.mode == mode)
                rooms.add(room);
        StringBuilder rs = new StringBuilder();
        rs.append(rooms.size());
        for (Room room : rooms)
            rs.append(' ').append(room.creator.name).append(' ').append(room.password == null ? 'N' : 'Y');
        connection.send(rs);
    }

    public void makeRoom(String text) {
        String[] parts = text.trim().split(" ");
        if (Room.getUserRoom(connection.user) == null) {
            if (parts.length == 1)
                new Room(connection.user, "", GameMode.valueOf(parts[0]));
            else if (parts.length == 2)
                new Room(connection.user, parts[1], GameMode.valueOf(parts[0]));
        }
    }

    public void enterRoom(String creator, String password) {
        User user = User.find(creator);
        if (user == null) {
            connection.send(EnterRoomStatus.INVALID_ROOM);
            return;
        }
        Room room = Room.getUserRoom(user);
        if (room == null) {
            connection.send(EnterRoomStatus.INVALID_ROOM);
            return;
        }
        if (room.state != RoomState.OPEN) {
            connection.send(EnterRoomStatus.INACTIVE_ROOM);
            return;
        }
        password = password.replace(" ", "");
        if (room.password != null && !room.password.equals(password)) {
            connection.send(EnterRoomStatus.INCORRECT_PASSWORD);
            return;
        }
        connection.send(EnterRoomStatus.SUCCESS);
        room.add(connection.user);
    }

    public void kick(String text) {
        User user = User.find(text.trim());
        Room room = Room.getUserRoom(connection.user);
        if (user == null || room == null || Room.getUserRoom(user) != room)
            return;
        AccessLevel kicker = room.getAccessLevel(connection.user);
        AccessLevel toKicked = room.getAccessLevel(user);
        if (kicker == AccessLevel.BOSS || (kicker == AccessLevel.MANAGER && toKicked == AccessLevel.USER))
            room.kick(user);
    }

    public void changeRoleToGamer() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            room.changeRoleToGamer(connection.user);
    }

    public void changeRoleToWatcher() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            room.changeRoleToWatcher(connection.user);
    }

    public void kicked() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            connection.send(listUsersNamesToString(room.kicked));
    }

    public void roomGamers() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            connection.send(mapUsersAccessToString(room.gamers));
    }

    public void roomWatchers() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            connection.send(mapUsersAccessToString(room.watchers));
    }

    public void roomChats() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            connection.send(chatsToString(filter(room.chats, connection.user)));
    }

    public void roomState() {
        Room room = Room.getUserRoom(connection.user);
        connection.send(room == null ? RoomState.FINISHED : room.state);
    }

    public void openRoom() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null && room.getAccessLevel(connection.user) == AccessLevel.BOSS && room.state == RoomState.CLOSE)
            room.open();
    }

    public void closeRoom() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null && room.getAccessLevel(connection.user) == AccessLevel.BOSS && room.state == RoomState.OPEN)
            room.close();
    }

    public void setManager(String name) {
        Room room = Room.getUserRoom(connection.user);
        if (room != null && room.getAccessLevel(connection.user) != AccessLevel.USER)
            room.setManager(User.find(name));
    }

    public void sayReady() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null && room.state == RoomState.CLOSE)
            room.sayReady(connection.user);
    }

    public void sayUnready() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null && room.state == RoomState.CLOSE)
            room.sayUnready(connection.user);
    }

    public void leaveRoom() {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            room.leave(connection.user);
    }

    public void toBeFriend(String name) {
        Room room = Room.getUserRoom(connection.user);
        User toBeFriend = User.find(name);
        if (room != null && room == Room.getUserRoom(toBeFriend) && toBeFriend != null) {
            toBeFriend.invitedFrom = connection.user;
            connection.user.invited = toBeFriend;
            if (connection.user == toBeFriend.invited) {
                toBeFriend.invited = connection.user.invited = null;
                toBeFriend.invitedFrom = connection.user.invitedFrom = null;
                toBeFriend.friends.add(connection.user);
                connection.user.friends.add(toBeFriend);
            }
        }
    }

    public void friendRequest() {
        if (connection.user.invitedFrom == null)
            connection.send("NOBODY");
        else {
            connection.send(connection.user.invitedFrom.name);
            connection.user.invitedFrom = null;
        }
    }

    public void roomMassage(String text) {
        Room room = Room.getUserRoom(connection.user);
        if (room != null)
            room.chats.add(new SMS(connection.user, SMS.makeRegular(text)));
    }

    public void getMassages(String name) {
        User user = User.find(name);
        if (user == null) return;
        connection.send(chatsToString(PvChats.getMessages(connection.user, user)));
    }

    public void sendMassage(String receiverS, String text) {
        User receiver = User.find(receiverS);
        if (receiver == null || !connection.user.friends.contains(receiver)
                || BlockManager.isBlocked(receiver, connection.user) || BlockManager.isBlocked(connection.user, receiver))
            return;
        PvChats.send(connection.user, receiver, SMS.makeRegular(text));
    }

    public void block(String name) {
        BlockManager.block(connection.user, User.find(name));
    }

    public void unblock(String name) {
        BlockManager.unblock(connection.user, User.find(name));
    }

    public void blockList() {
        List<User> blockedFriends = new ArrayList<>();
        for (User friend : connection.user.friends)
            if (BlockManager.isBlocked(connection.user, friend))
                blockedFriends.add(friend);
        connection.send(listUsersNamesToString(blockedFriends));
    }

    public void blockerList() {
        List<User> blockerFriends = new ArrayList<>();
        for (User friend : connection.user.friends)
            if (BlockManager.isBlocked(friend, connection.user))
                blockerFriends.add(friend);
        connection.send(listUsersNamesToString(blockerFriends));
    }


    private List<SMS> filter(List<SMS> chats, User user) {
        List<SMS> filtered = new ArrayList<>();
        for (SMS msg : chats)
            if (!msg.text.contains("@") || msg.text.contains("@" + user.name) || user == msg.user)
                filtered.add(msg);
        return filtered;
    }

    private String chatsToString(List<SMS> chat) {
        StringBuilder rs = new StringBuilder(chat.size() + "");
        for (SMS sms : chat)
            rs.append(" ").append(sms.user.name).append(" ").append(SMS.makeInLine(sms.text));
        return rs.toString();
    }

    private String listUsersNamesToString(List<User> users) {
        StringBuilder rs = new StringBuilder(users.size() + "");
        for (User user : users)
            rs.append(" ").append(user.name);
        return rs.toString();
    }

    private String mapUsersAccessToString(Map<User, AccessLevel> map) {
        StringBuilder rs = new StringBuilder(map.size() + "");
        for (Map.Entry<User, AccessLevel> e : map.entrySet())
            rs.append(" ").append(e.getKey().name).append(" ").append(e.getValue());
        return rs.toString();
    }


    // FIXME: connection user can be null
}
