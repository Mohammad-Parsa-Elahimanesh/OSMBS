public class User {
    public final String name;
    public final int password;

    public User(String name, String password) {
        this.name = name;
        this.password = password.hashCode();
    }
}
