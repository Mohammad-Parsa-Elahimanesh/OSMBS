package logic.manager;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manager {
    public static final int SERVER_PORT = 50000;
    public static final Map<Constants, Double> constants = new EnumMap<>(Constants.class);

    private Manager() {
    }

    public static void calculateConstants() {
        try {
            Scanner scannerOnConstants = new Scanner(new File("src\\variables.txt"));
            while (scannerOnConstants.hasNextLine()) {
                String[] lines = scannerOnConstants.nextLine().split(" ");
                constants.put(Constants.valueOf(lines[0].replace(".", "_").toUpperCase()), Double.parseDouble(lines[1]));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
