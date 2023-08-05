package logic;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class SMS {
    static final String SPACE = "!%#$@";
    static final String LINE = "*%^&%*";
    static final Map<User, Color> senderColor = new HashMap<>();
    static final Color[] colors = {Color.green, Color.blue, Color.yellow, Color.red, Color.black};
    public final User user;
    public final String text;
    public SMS(User user, String text) {
        this.user = user;
        this.text = text;
        senderColor.computeIfAbsent(user, e -> colors[senderColor.size()%colors.length]);
    }

    public static String makeInLine(String text) {
        text = text.replace(" ", SPACE);
        text = text.replace("\n", LINE);
        return text;
    }

    public static String makeRegular(String text) {
        text = text.replace(SPACE, " ");
        text = text.replace(LINE, "\n");
        return text;
    }
}
