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
             case SIGN_UP -> connection.request.signUp(connection.scanner.nextLine());
             case SIGN_IN -> connection.request.signIn(connection.scanner.nextLine());
             case UPDATE_USER -> {}// TODO;
             case SIGN_OUT -> connection.user = null;
             case CLOSE -> running = false;
         }
    }

    void send(String data) {
        if (connection.printer == null)
            return;
        connection.printer.println(data);
        connection.printer.flush();
    }
}
