package logic;

public class SMS {
    static final String SPACE = "!%#$";
    static final String LINE = "*%^&%*";
    public final User user;
    public final String text;
    public final long sendingTime;

    public SMS(User user, String text) {
        this.user = user;
        this.text = text;
        sendingTime = System.currentTimeMillis();
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
