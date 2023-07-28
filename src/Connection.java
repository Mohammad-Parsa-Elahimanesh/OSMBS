import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Connection {
    private static int idCounter = 0;
    final Socket socket;
    final Scanner scanner;
    final PrintStream printer;
    final int id;
    User user;

    Connection(Socket socket) throws IOException {
        this.socket = socket;
        scanner = new Scanner(new BufferedInputStream(socket.getInputStream()));
        printer = new PrintStream(new BufferedOutputStream(socket.getOutputStream()));
        this.id = idCounter++;
    }
}
