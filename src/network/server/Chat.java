package network.server;

import network.request.Request;
import network.request.RequestType;

public class Chat extends Thread {
    final Connection connection;
    final ChatCenter manager;
    boolean running = true;

    Chat(Connection connection, ChatCenter manager) {
        this.connection = connection;
        this.manager = manager;

    }

    @Override
    public void run() {
        while (running)
            listen();
        synchronized (manager.oldConnections) {
            manager.oldConnections.add(connection);
        }
    }

    void listen() {
         RequestType requestType = RequestType.valueOf(connection.scanner.next());
         switch (requestType) {
             case USERS -> connection.request.users();
             case FRIENDS -> connection.request.friends();
             case SIGN_UP -> connection.request.signUp(connection.scanner.nextLine());
             case SIGN_IN -> connection.request.signIn(connection.scanner.nextLine());
             case SIGN_OUT -> connection.user = null;
             case UPDATE_OFFLINE_COINS -> connection.request.updateOfflineCoins(connection.scanner.nextInt());
             case UPDATE_RECORDS -> connection.request.updateRecords(connection.scanner.nextLine());
             case COINS -> connection.request.coins();
             case RECORDS -> connection.request.records();
             case CLOSE -> running = false;


             case ROOMS -> connection.request.rooms(connection.scanner.next());
             case MAKE_ROOM -> connection.request.makeRoom(connection.scanner.nextLine());
             case ENTER_ROOM -> connection.request.enterRoom(connection.scanner.next(), connection.scanner.nextLine());
             case KICK -> connection.request.kick(connection.scanner.nextLine());
             case CHANGE_ROLE_TO_GAMER -> connection.request.changeRoleToGamer();
             case CHANGE_ROLE_TO_WATCHER -> connection.request.changeRoleToWatcher();
             case KICKED -> connection.request.kicked();
             case ROOM_CHATS -> connection.request.roomChats();
             case ROOM_GAMERS -> connection.request.roomGamers();
             case ROOM_WATCHERS -> connection.request.roomWatchers();
             case ROOM_STATE -> connection.request.roomState();
             case OPEN_ROOM -> connection.request.openRoom();
             case CLOSE_ROOM -> connection.request.closeRoom();
             case SET_MANAGER -> connection.request.setManager(connection.scanner.next());
             case SAY_READY -> connection.request.sayReady();
             case SAY_UNREADY -> connection.request.sayUnready();
             case LEAVE_ROOM -> connection.request.leaveRoom();
             case TO_BE_FRIEND -> connection.request.toBeFriend(connection.scanner.next());
             case FRIEND_INVITATION -> connection.request.friendRequest();
             case ROOM_MASSAGE -> connection.request.roomMassage(connection.scanner.next());
         }
    }

}
