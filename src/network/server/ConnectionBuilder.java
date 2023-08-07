package network.server;

import logic.manager.Manager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionBuilder extends Thread {
    ServerSocket serverSocket;
    ChatCenter chatCenter = new ChatCenter();

    public void run() {
        try {
            serverSocket = new ServerSocket(Manager.SERVER_PORT);
            chatCenter.start();
            while (true) {
                Socket socket = serverSocket.accept();
                Connection newConnection = new Connection(socket);
                synchronized (newConnection) {
                    chatCenter.newConnections.add(newConnection);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}