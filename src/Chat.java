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
        while (running) {
            listen();
            answer();
        }
        synchronized (manager.oldConnections) {
            manager.oldConnections.add(connection);
        }
    }

    void listen() {
        //String input = connection.scanner.next();

    }

    void answer() {
        // document why this method is empty
    }

    void send(String data) {
        if (connection.printer == null)
            return;
        connection.printer.println(data);
        connection.printer.flush();
    }
}
