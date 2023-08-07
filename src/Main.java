import logic.manager.Manager;
import network.server.ConnectionBuilder;

public class Main {
    public static void main(String[] args) {
        Manager.calculateConstants();
        new ConnectionBuilder().start();
    }
}